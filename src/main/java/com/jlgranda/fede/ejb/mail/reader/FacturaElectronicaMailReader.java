/*
 * Copyright (C) 2015 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.jlgranda.fede.ejb.mail.reader;

import com.jlgranda.fede.ejb.SettingService;
import com.jlgranda.fede.ejb.url.reader.FacturaElectronicaURLReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.dom.Entity;
import org.apache.james.mime4j.dom.MessageBuilder;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import org.jpapi.model.profile.Subject;
import org.jlgranda.fede.util.FacturaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jlgranda.fede.sri.jaxb.factura.v110.Factura;

/**
 *
 * @author jlgranda
 */
@Stateless
public class FacturaElectronicaMailReader {

    Logger logger = LoggerFactory.getLogger(FacturaElectronicaMailReader.class);

    @EJB
    SettingService settingService;

    /**
     * Leer el inbox de <tt>Subject</tt>
     *
     * @param _subject la instancia <tt>Subject</tt> para la cual ingresar al
     * inbox y cargar las facturas
     * @param folder
     * @return Lista de objetos <tt>FacturaReader</tt>
     * @throws javax.mail.MessagingException
     * @throws java.io.IOException
     */
    public List<FacturaReader> read(Subject _subject, String folder) throws MessagingException, IOException {

        List<FacturaReader> result = new ArrayList<>();
        String server = settingService.findByName("mail.imap.host").getValue();
        //Todo definir una mejor forma de manejar la cuenta de correo
        String username = _subject.getFedeEmail();
        String password = _subject.getFedeEmailPassword();
        //String port = settingService.findByName("mail.imap.port").getValue();

        //String proto = "true".equalsIgnoreCase(settingService.findByName("mail.smtp.starttls.enable").getValue()) ? "TLS" : null;

        ///logger.info("Conectanto a servidor de correo # {}:\n\t Username: {}\n\t Password: {}\n\t ", server, username, password);
        IMAPClient client = new IMAPClient(server, username, password);

        Address[] fromAddress = null;
        String from = "";
        String subject = "";
        String sentDate = "";
        FacturaReader facturaReader = null;
        String[] token = null;
        List<String> urls = new ArrayList<>(); //Guardar enlaces a factura si es el caso
        boolean facturaEncontrada = false;
        Factura factura = null;
        EmailHelper emailHelper = new EmailHelper();
        MessageBuilder builder = new DefaultMessageBuilder();
        ByteArrayOutputStream os = null;
        for (Message message : client.getMessages(folder, false)) {
            //attachFiles = "";
            fromAddress = message.getFrom();
            from = fromAddress[0].toString();
            subject = message.getSubject();
            sentDate = message.getSentDate() != null ? message.getSentDate().toString() : "";

            facturaEncontrada = false;

            try {
                org.apache.james.mime4j.dom.Message mime4jMessage = builder.parseMessage(new ByteArrayInputStream(emailHelper.fullMail(message).getBytes()));
                result.addAll(handleMessage(mime4jMessage));
            } catch (org.apache.james.mime4j.MimeIOException | org.apache.james.mime4j.MimeException ex) {
                logger.error("Fail to read message: " + subject, ex);
            } catch (Exception ex) {
                logger.error("Fail to read message with General Exception: " + subject, ex);
            }
        }

        client.close();

        logger.info("Readed {} email messages!", result.size());

        return result;
    }

    /**
     * Obtiene una lista de objetos <tt>FacturaReader</tt> desde el mensaje de correo, si existe
     *
     * @param mime4jMessage
     * @return lista de instancias instancia <tt>FacturaReader</tt> si existe la factura, null
     * en caso contrario
     */
    private List<FacturaReader> handleMessage(org.apache.james.mime4j.dom.Message mime4jMessage) throws IOException, Exception {
        List<FacturaReader> result = new ArrayList<>();
        ByteArrayOutputStream os = null;
        String filename = null;
        Factura factura = null;
        EmailHelper emailHelper = new EmailHelper();
        if (mime4jMessage.isMultipart()) {
            org.apache.james.mime4j.dom.Multipart mime4jMultipart = (org.apache.james.mime4j.dom.Multipart) mime4jMessage.getBody();
            emailHelper.parseBodyParts(mime4jMultipart);
            //Obtener la factura en los adjuntos
            if (emailHelper.getAttachments().isEmpty()) {
                //If it's single part message, just get text body  
                String text = emailHelper.getHtmlBody().toString();
                emailHelper.getTxtBody().append(text);
                if (mime4jMessage.getSubject().contains("Ghost")) {

                    String url = FacturaUtil.extraerURL(emailHelper.getHtmlBody().toString(), "<a href=\"", "\" target=\"_blank\">Descarga formato XML</a>");
                    if (url != null) {
                        result.add(FacturaElectronicaURLReader.getFacturaElectronica(url));
                    }
                }
            } else {
                for (Entity entity : emailHelper.getAttachments()) {
                    filename = EmailHelper.getFilename(entity);

                    //if (entity.getBody() instanceof BinaryBody) {
                    if (("application/octet-stream".equalsIgnoreCase(entity.getMimeType())
                            || "application/xml".equalsIgnoreCase(entity.getMimeType())
                            || "text/xml".equalsIgnoreCase(entity.getMimeType())
                            || "text/plain".equalsIgnoreCase(entity.getMimeType()))
                            && (filename != null && filename.endsWith(".xml"))) {
                        //attachFiles += part.getFileName() + ", ";
                        os = EmailHelper.writeBody(entity.getBody());
                        factura = FacturaUtil.read(os.toString());
                        if (factura != null) {
                            result.add(new FacturaReader(factura, os.toString(), entity.getFilename(), mime4jMessage.getFrom().get(0).getAddress()));
                        }
                    } else if (("application/octet-stream".equalsIgnoreCase(entity.getMimeType())
                            || "aplication/xml".equalsIgnoreCase(entity.getMimeType())
                            || "text/xml".equalsIgnoreCase(entity.getMimeType()))
                            && (filename != null && filename.endsWith(".zip"))) {
                        //http://www.java2s.com/Tutorial/Java/0180__File/UnzipusingtheZipInputStream.htm    
                        os = EmailHelper.writeBody(entity.getBody());
                        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(os.toByteArray()));
                        try {
                            ZipEntry entry = null;
                            String tmp = null;
                            ByteArrayOutputStream fout = null;
                            while ((entry = zis.getNextEntry()) != null) {
                                if (entry.getName().endsWith(".xml")) {
                                    //logger.debug("Unzipping {}", entry.getFilename());
                                    fout = new ByteArrayOutputStream();
                                    for (int c = zis.read(); c != -1; c = zis.read()) {
                                        fout.write(c);
                                    }

                                    tmp = new String(fout.toByteArray(), Charset.defaultCharset());

                                    factura = FacturaUtil.read(tmp);
                                    if (factura != null) {
                                        result.add(new FacturaReader(factura, tmp, entity.getFilename()));
                                    }
                                    fout.close();
                                }
                                zis.closeEntry();
                            }
                            zis.close();

                        } finally {
                            IOUtils.closeQuietly(os);
                            IOUtils.closeQuietly(zis);
                        }
                    } else if ("message/rfc822".equalsIgnoreCase(entity.getMimeType())) {
                        if (entity.getBody() instanceof org.apache.james.mime4j.message.MessageImpl) {
                            result.addAll(handleMessage((org.apache.james.mime4j.message.MessageImpl) entity.getBody()));
                        }
                    }
                }
            }
        } else {
            //If it's single part message, just get text body  
            String text = emailHelper.getTxtPart(mime4jMessage);
            emailHelper.getTxtBody().append(text);
            if (mime4jMessage.getSubject().contains("Ghost")) {

                String url = FacturaUtil.extraerURL(emailHelper.getHtmlBody().toString(), "<a href=\"", "\" target=\"_blank\">Descarga formato XML</a>");
                if (url != null) {
                    result.add(FacturaElectronicaURLReader.getFacturaElectronica(url));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws MessagingException, IOException, Exception {

        FacturaElectronicaMailReader famr = new FacturaElectronicaMailReader();
        String server = "jlgranda.com";
        String username = "1104499049@jlgranda.com";
        String password = "FKR5oznrtVwnEirkrbl4rmeba0mFCmYh";

        String proto = "TLS";
        IMAPClient client = new IMAPClient(server, username, password);
        String contentType = null;
        StringReader reader = null;
        Address[] fromAddress = null;
        String messageContent = "";
        String attachFiles = "";
        org.apache.james.mime4j.dom.Multipart multiPart = null;
        MimeBodyPart part = null;
        int i = 0;
        String partContentType = null;
        String partName = null;
        String from = "";
        String subject = "";
        String sentDate = "";
        int contadorFacturasLeidas = 0;
        List<FacturaReader> result = new ArrayList<>(); //Guardar enlaces a factura si es el caso

        boolean facturaEncontrada = false;
        int index = 0;
        int numberOfParts = 0;
        String[] token = null;
        MessageBuilder builder = new DefaultMessageBuilder();
        ByteArrayOutputStream os = null;
        EmailHelper emailHelper = new EmailHelper();
        for (Message message : client.getMessages("inbox", false)) {
            //System.out.println("Message #" + email.fullMail(message) + ":");

            //attachFiles = "";
            fromAddress = message.getFrom();
            from = fromAddress[0].toString();
            subject = message.getSubject();
            sentDate = message.getSentDate() != null ? message.getSentDate().toString() : "";
            if (subject.contains("Fwd: Ghost - Doc. Electrónico: 2201201601180145025300120010320000336391234567819")) {
                System.out.println("--------------------------------------" + (index++) + "-----------------------------------------");
                System.out.println("From: " + fromAddress);
                System.out.println("Subject: " + subject);
                try {
                    org.apache.james.mime4j.dom.Message mime4jMessage = builder.parseMessage(new ByteArrayInputStream(emailHelper.fullMail(message).getBytes()));
                    result.addAll(famr.handleMessage(mime4jMessage));
                } catch (org.apache.james.mime4j.MimeIOException mioe) {
                    mioe.printStackTrace();
                } catch (org.apache.james.mime4j.MimeException me) {
                    me.printStackTrace();
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
        System.err.println("Facturas encontradas>> " + result.size());
        client.close();

    }
}
