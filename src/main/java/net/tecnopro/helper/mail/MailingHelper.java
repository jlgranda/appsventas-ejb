package net.tecnopro.helper.mail;

import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Ayudador para envio de correos y notificaciones
 *
 * @author jlgranda
 *
 */
public class MailingHelper {

    private MailingHelper() {
        super();
    }

    /**
     * Envia correos de texto, empleando el API JavaMail directamente.
     *
     * @param host
     * @param port
     * @param username
     * @param remitente el nombre del remitente del mensaje
     * @param destinatario la lista de destinatarios del mensaje
     * @param asunto el titulo del mensaje de correo electrónico
     * @param mensaje el texto del mensaje, no incluye archivos
     * @return
     */
    public static boolean enviar(String host, String port, String username,
            String password, String remitente, String[] destinatario,
            String asunto, String mensaje) {

        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.port", port);
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(remitente));
            InternetAddress[] address = new InternetAddress[destinatario.length];
            for (int i = 0; i < destinatario.length; i++) {
                address[i] = new InternetAddress(destinatario[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(asunto);
            msg.setSentDate(Calendar.getInstance().getTime());

            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(mensaje);

            // // create and fill the second message part
            // MimeBodyPart mbp2 = new MimeBodyPart();
            // // Use setText(text, charset), to show it off !
            // mbp2.setText(msgText2, "us-ascii");
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            // mp.addBodyPart(mbp2);

            // add the Multipart to the message
            msg.setContent(mp);

            // send the message
            // Transport tr = session.getTransport("smtps");
            Transport tr = session.getTransport("smtp");
            int _port = Integer.parseInt(port);
            tr.connect(host, _port, username, password);
            msg.saveChanges(); // don't forget this
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();

        } catch (MessagingException mex) {
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
            }
            return false;
        }

        return true;
    }

    /**
     * Envia correos de texto, empleando el API JavaMail directamente.
     *
     * @param host
     * @param remitente el nombre del remitente del mensaje
     * @param username
     * @param password
     * @param smtpAuth
     * @param tlsEnable
     * @param port
     * @param destinatario la lista de destinatarios del mensaje
     * @param asunto el titulo del mensaje de correo electrónico
     * @param mensaje el texto del mensaje, no incluye archivos
     * @return 
     */
    public static boolean enviar(String host, String port, String username,
            String password, String smtpAuth, String tlsEnable,
            String remitente, String destinatario, String asunto, String mensaje) {

        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.port", port);
        if (smtpAuth.compareTo("true") == 0) {
            props.put("mail.smtp.auth", "true");
        }
        if (tlsEnable.compareTo("true") == 0) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(remitente));

            InternetAddress[] address = {new InternetAddress(destinatario)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(asunto);
            msg.setSentDate(Calendar.getInstance().getTime());

            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(mensaje, "text/html");
            mp.addBodyPart(htmlPart);

            // add the Multipart to the message
            msg.setContent(mp);

            // send the message
            Transport tr = session.getTransport("smtp");
            int _port = Integer.parseInt(port);
            tr.connect(host, _port, username, password);
            msg.saveChanges(); // don't forget this
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();

        } catch (MessagingException mex) {
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
            }
            return false;
        }

        return true;
    }

    /**
     * Envia correos de texto, empleando el API JavaMail directamente.
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param smtpAuth
     * @param tlsEnable
     * @param remitente el nombre del remitente del mensaje
     * @param destinatario la lista de destinatarios del mensaje
     * @param asunto el titulo del mensaje de correo electrónico
     * @param mensaje el texto del mensaje, no incluye archivos
     * @param archivo
     * @return 
     */
    public static boolean enviarAttachment(String host, String port, String username,
            String password, String smtpAuth, String tlsEnable, String remitente,
            String destinatario, String asunto, String mensaje, String archivo) {

        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.port", port);
        if (smtpAuth.compareTo("true") == 0) {
            props.put("mail.smtp.auth", "true");
        }
        if (tlsEnable.compareTo("true") == 0) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(remitente));

            InternetAddress[] address = {new InternetAddress(destinatario)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(asunto);
            msg.setSentDate(Calendar.getInstance().getTime());

            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(mensaje);

            // // create and fill the second message part
            // MimeBodyPart mbp2 = new MimeBodyPart();
            // // Use setText(text, charset), to show it off !
            // mbp2.setText(msgText2, "us-ascii");
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            // mp.addBodyPart(mbp2);

            // Part two is attachment
            mbp1 = new MimeBodyPart();
            DataSource source = new FileDataSource(archivo);
            mbp1.setDataHandler(new DataHandler(source));
            mbp1.setFileName(archivo);
            mp.addBodyPart(mbp1);

            // add the Multipart to the message
            msg.setContent(mp);

            // send the message
            // Transport tr = session.getTransport("smtps");
            Transport tr = session.getTransport("smtp");
            int _port = Integer.parseInt(port);
            tr.connect(host, _port, username, password);
            msg.saveChanges(); // don't forget this
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();

        } catch (MessagingException mex) {
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
            }
            return false;
        }

        return true;
    }

}
