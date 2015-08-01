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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import org.apache.commons.io.IOUtils;
import org.jpapi.model.profile.Subject;
import org.jlgranda.fede.util.FacturaUtil;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Obtiene la lista de objetos factura para el sujeto en fede
     *
     * @param _subject la instancia <tt>Subject</tt> para la cual ingresar al
     * inbox y cargar las facturas
     * @return
     */
    public List<FacturaReader> getFacturasElectronicas(Subject _subject) throws MessagingException, IOException {

        List<FacturaReader> result = new ArrayList<>();
        String server = settingService.findByName("mail.imap.host").getValue();
        //Todo definir una mejor forma de manejar la cuenta de correo
        String username = _subject.getFedeEmail();
        String password = _subject.getFedeEmailPassword();
        String port = settingService.findByName("mail.imap.port").getValue();

        String proto = "true".equalsIgnoreCase(settingService.findByName("mail.smtp.starttls.enable").getValue()) ? "TLS" : null;

        logger.info("Conectanto a servidor de correo # {}:\n\t Username: {}\n\t Password: {}\n\t ", server, username, password);

        IMAPClient client = new IMAPClient(server, username, password);

        String contentType = null;
        StringReader reader = null;
        Address[] fromAddress = null;
        String messageContent = "";
        String attachFiles = "";
        Multipart multiPart = null;
        MimeBodyPart part = null;
        int i = 0;
        String attachmentContentType = null;
        String attachmentName = null;
        String from = "";
        String subject = "";
        String sentDate = "";
        FacturaReader facturaReader = null;
        String[] token = null;
        for (Message message : client.getMessages("inbox", false)) {
            attachFiles = "";
            fromAddress = message.getFrom();
            from = fromAddress[0].toString();
            subject = message.getSubject();
            sentDate = message.getSentDate() != null ? message.getSentDate().toString() : "";

            contentType = message.getContentType();

            if (contentType.contains("multipart")) {
                // content may contain attachments
                multiPart = (Multipart) message.getContent();
                int numberOfParts = multiPart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    if (MimeBodyPart.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

                        logger.info("part.getContentType: {}", part.getContentType());

                        token = part.getContentType().split(";");

                        attachmentContentType = token[0];

                        if (token.length == 3) {
                            attachmentName = token[2];
                        } else {
                            attachmentName = token[1];
                        }

                        if (("application/octet-stream".equalsIgnoreCase(attachmentContentType)
                                || "aplication/xml".equalsIgnoreCase(attachmentContentType)
                                || "text/xml".equalsIgnoreCase(attachmentContentType))
                                && attachmentName.endsWith(".xml")) {
                            attachFiles += part.getFileName() + ", ";
                            StringWriter writer = new StringWriter();
                            IOUtils.copy(part.getInputStream(), writer, "UTF-8");
                            facturaReader = new FacturaReader(FacturaUtil.read(writer.toString()), writer.toString(), part.getFileName());
                            result.add(facturaReader);
                            IOUtils.closeQuietly(part.getInputStream());
                        } else if (("application/octet-stream".equalsIgnoreCase(attachmentContentType)
                                || "aplication/xml".equalsIgnoreCase(attachmentContentType)
                                || "text/xml".equalsIgnoreCase(attachmentContentType))
                                && attachmentName.endsWith(".zip")) {
                            //http://www.java2s.com/Tutorial/Java/0180__File/UnzipusingtheZipInputStream.htm    
                            ZipInputStream zis = new ZipInputStream(part.getInputStream());
                            try {
                                ZipEntry entry = null;
                                String tmp = null;
                                ByteArrayOutputStream fout = null;
                                while ((entry = zis.getNextEntry()) != null) {
                                    if (entry.getName().endsWith(".xml")) {
                                        logger.debug("Unzipping {}", entry.getName());
                                        fout = new ByteArrayOutputStream();
                                        for (int c = zis.read(); c != -1; c = zis.read()) {
                                            fout.write(c);
                                        }

                                        tmp = new String(fout.toByteArray(), Charset.defaultCharset());
                                        logger.debug("Contenidos {}", tmp);
                                        facturaReader = new FacturaReader(FacturaUtil.read(tmp), tmp, part.getFileName());
                                        result.add(facturaReader);
                                        fout.close();
                                    }
                                    zis.closeEntry();
                                }
                                zis.close();

                            } finally {
                                IOUtils.closeQuietly(part.getInputStream());
                                IOUtils.closeQuietly(zis);
                            }
                        } else {
                            //TODO procesar otros tipos de archivos
                        }

                    } else {
                        // this part may be the message content
                        messageContent = part.getContent().toString();
                        //System.out.println("jlgrand.com --> " + messageContent);
                    }
                }

                if (attachFiles.length() > 1) {
                    attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                }
            } else if (contentType.contains("text/plain")
                    || contentType.contains("text/html")) {
                Object content = message.getContent();
                if (content != null) {
                    messageContent = content.toString();
                }
            }

            // print out details of each message
            logger.debug("Message # {}:\n\t From: {}\n\t Subject: {}\n\t Sent Date: {}\n\t Message: {}\n\t Attachments: {}", (i + 1), from, subject, sentDate, messageContent, attachFiles);
            i++;
        }

        client.close();

        logger.info("Read {} email messages", result.size());
            
        return result;
    }

    public static void main(String[] args) throws MessagingException, IOException {

        String server = "jlgranda.com";
        String username = "1104499049@jlgranda.com";
        String password = "sBWzwSm7az4gn3kV8crFj4FbGpqd4izt";

        String proto = "TLS";
        com.jlgranda.fede.ejb.mail.reader.IMAPClient client = new com.jlgranda.fede.ejb.mail.reader.IMAPClient(server, username, password);
        String contentType = null;
        StringReader reader = null;
        Address[] fromAddress = null;
        String messageContent = "";
        String attachFiles = "";
        Multipart multiPart = null;
        MimeBodyPart part = null;
        int i = 0;
        String attachmentContentType = null;
        String attachmentName = null;
        for (Message message : client.getMessages("inbox", false)) {
            fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            String subject = message.getSubject();
            String sentDate = message.getSentDate() != null ? message.getSentDate().toString() : "";

            contentType = message.getContentType();

            if (contentType.contains("multipart")) {
                // content may contain attachments
                multiPart = (Multipart) message.getContent();
                int numberOfParts = multiPart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    if (MimeBodyPart.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        System.out.println("jlgranda.com --> part.getContentType: " + part.getContentType());
                        attachmentContentType = part.getContentType().split(";")[0];
                        attachmentName = part.getContentType().split(";")[1];
                        if (("application/octet-stream".equalsIgnoreCase(attachmentContentType)
                                || "aplication/xml".equalsIgnoreCase(attachmentContentType)
                                || "text/xml".equalsIgnoreCase(attachmentContentType))
                                && attachmentName.endsWith(".xml")) {
                            attachFiles += part.getFileName() + ", ";
                            StringWriter writer = new StringWriter();
                            IOUtils.copy(part.getInputStream(), writer, "UTF-8");
                            //System.out.println("jlgrand.com --> " + writer.toString());
                            IOUtils.closeQuietly(part.getInputStream());
                        } else if (("application/octet-stream".equalsIgnoreCase(attachmentContentType)
                                || "aplication/xml".equalsIgnoreCase(attachmentContentType)
                                || "text/xml".equalsIgnoreCase(attachmentContentType))
                                && attachmentName.endsWith(".zip")) {

                            ZipInputStream zis = new ZipInputStream(part.getInputStream());

                            StringWriter writer = null;
                            File file = null;
                            String outputDir = "/tmp";
                            try {
                                ZipEntry entry = null;
                                while ((entry = zis.getNextEntry()) != null) {
                                    if (entry.getName().endsWith(".xml")) {
                                        System.out.println("Unzipping " + entry.getName());
                                        ByteArrayOutputStream fout = new ByteArrayOutputStream();
                                        for (int c = zis.read(); c != -1; c = zis.read()) {
                                            fout.write(c);
                                        }
                                        System.out.println("----> " + new String(fout.toByteArray(), Charset.defaultCharset()));
                                        fout.close();
                                    }
                                    zis.closeEntry();

                                }
                                zis.close();

                            } finally {
                                IOUtils.closeQuietly(part.getInputStream());
                                IOUtils.closeQuietly(zis);
                            }
                        } else {
                            //TODO procesar otros archivos
                        }

                    } else {
                        // this part may be the message content
                        messageContent = part.getContent().toString();
                        System.out.println("jlgrand.com --> " + messageContent);
                    }
                }

                if (attachFiles.length() > 1) {
                    attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                }
            } else if (contentType.contains("text/plain")
                    || contentType.contains("text/html")) {
                Object content = message.getContent();
                if (content != null) {
                    messageContent = content.toString();
                }
            }

            // print out details of each message
            System.out.println("Message #" + (i + 1) + ":");
            System.out.println("\t From: " + from);
            System.out.println("\t Subject: " + subject);
            System.out.println("\t Sent Date: " + sentDate);
            System.out.println("\t Message: " + messageContent);
            System.out.println("\t Attachments: " + attachFiles);
            i++;
        }

        client.close();

    }
}
