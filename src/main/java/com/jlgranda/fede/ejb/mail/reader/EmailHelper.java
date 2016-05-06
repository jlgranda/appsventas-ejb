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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.codec.DecoderUtil;
import org.apache.james.mime4j.dom.Body;
import org.apache.james.mime4j.dom.Entity;
import org.apache.james.mime4j.dom.Multipart;
import org.apache.james.mime4j.dom.SingleBody;
import org.apache.james.mime4j.dom.TextBody;
import org.apache.james.mime4j.dom.address.MailboxList;
import org.apache.james.mime4j.dom.field.ContentTypeField;
import org.apache.james.mime4j.message.MessageImpl;


/**
 *
 * @author jlgranda
 * @see http 
 *      ://www.mozgoweb.com/posts/how-to-parse-mime-message-using-mime4j-library
 *      /
 */
public class EmailHelper {
 public static boolean debug = true;
    public static final Logger LOGGER = Logger
            .getLogger("com.jlgranda.fede.ejb.mail.reader.EMailHelper");
    private StringBuffer txtBody = new StringBuffer();
    private StringBuffer htmlBody = new StringBuffer();
    private ArrayList<Entity> attachments = new ArrayList<>();

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        EmailHelper.debug = debug;
    }

    public StringBuffer getTxtBody() {
        return txtBody;
    }

    public void setTxtBody(StringBuffer txtBody) {
        this.txtBody = txtBody;
    }

    public StringBuffer getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(StringBuffer htmlBody) {
        this.htmlBody = htmlBody;
    }

    public ArrayList<Entity> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Entity> attachments) {
        this.attachments = attachments;
    }

    /**
     * get a String from an input Stream
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String fromInputStream(InputStream inputStream) throws IOException {
        String result = IOUtils.toString(inputStream);
        return result;
    }

    /**
     * get the full Mail from a message
     * 
     * @param message
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public String fullMail(javax.mail.Message message) throws MessagingException,
            IOException {
        StringBuffer sBuf = new StringBuffer();
        @SuppressWarnings("unchecked")
        Enumeration<javax.mail.Header> headers = message.getAllHeaders();
        while (headers.hasMoreElements()) {
            javax.mail.Header header = headers.nextElement();
            sBuf.append(header.getName()).append(": ").append(header.getValue()).append("\n");
        }
        sBuf.append(fromInputStream(message.getInputStream()));
        return sBuf.toString();
    }

    /**
     * Authentication
     */
    public static class Authentication {
        enum AuthenticationType {
            pop3, smtp
        };

        String host;
        String user;
        String password;
        AuthenticationType authenticationType;
        Transport mTransport;

        /**
         * create an Authentication from the configuration
         * 
         * @param configuration
         * @param pAuthType
         */
        public Authentication(String _host, String _user, String _password,
                AuthenticationType pAuthType) {
            authenticationType = pAuthType;
            String prefix = pAuthType.name() + ".";
            // use prefix e.g. pop3.host / smtp.host
            host = _host;
            user = _user;
            password = _password;
        }

        /**
         * authenticate for sending / receiving e-mail
         * 
      * @return 
         * @throws MessagingException
         */
        public Transport authenticate() throws MessagingException {
            Properties lProps = new Properties();
            Session session = Session.getDefaultInstance(lProps);
            switch (authenticationType) {
            case pop3:
                Store store = session.getStore("pop3");
                store.connect(host, user, password);
                store.close();
                return null;
            case smtp:
                // http://javamail.kenai.com/nonav/javadocs/com/sun/mail/smtp/package-summary.html
                mTransport = session.getTransport("smtp");
                mTransport.connect(host, user, password);
                return mTransport;
            }
            return null;
        }
    }
    /**
     * strip the brackets
     * 
     * @param addressList
     * @return
     */
    public String stripBrackets(MailboxList addressList) {
        String result = null;
        if (addressList != null) {
            result = addressList.toString();
            if (result.startsWith("[") && result.endsWith("]")) {
                result = result.substring(1, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * write the given body to the given fileoutput stream
     * 
     * @param body
     * @return 
     * @throws IOException
     */
    public static ByteArrayOutputStream writeBody(Body body) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (body instanceof SingleBody) {
            ((SingleBody) body).writeTo(os);
        } else if (body instanceof MessageImpl) {
            os = writeBody(((MessageImpl) body).getBody());
        } else {
            LOGGER.log(Level.WARNING, "can't handle body of type "
                    + body.getClass().getSimpleName());
        }
        
        return os;
    }

    /**
     * This method classifies bodyPart as text, html or attached file
     * 
     * @param multipart
     * @throws IOException
     */
    public void parseBodyParts(Multipart multipart) throws IOException {
        // loop over the parts
        for (Entity part : multipart.getBodyParts()) {
            String mimeType = part.getMimeType();
            if (mimeType.equals("text/plain")) {
                String txt = getTxtPart(part);
                txtBody.append(txt);
            } else if (mimeType.equals("text/html")) {
                String html = getTxtPart(part);
                htmlBody.append(html);
            } else if (part.getDispositionType() != null
                    && !part.getDispositionType().equals("")) {
                // If DispositionType is null or empty, it means that it's multipart,
                // not attached file
                attachments.add(part);
            }

            // If current part contains other, parse it again by recursion
            if (part.isMultipart()) {
                parseBodyParts((Multipart) part.getBody());
            }
        }
    }

    /**
     * 
     * @param part
     * @return
     * @throws IOException
     */
    public String getTxtPart(Entity part) throws IOException {
        // Get content from body
        TextBody tb = (TextBody) part.getBody();
        return this.fromInputStream(tb.getInputStream());
    }

    public static String getFilename (final Entity entity)
    {
        // todo: Track Mime4j-109 - Add support for RFC-2231 (MIME Parameter Value Encoded Word Extensions)
        String filename = entity.getFilename();

        if (filename == null)
        {
            final ContentTypeField contentTypeField = (ContentTypeField) entity.getHeader().getField("Content-Type");
            filename = contentTypeField.getParameter("name");
        }

        // see EMSUI-614
        if (filename != null) {
            filename = DecoderUtil.decodeEncodedWords(filename, null);

            // note: if combining LTR strings and RTL strings, DecoderUtil#decodeEncodedWords could return
            //        a string with some Unicode control characters. Sanitize this string.
            filename = sanitizeString(filename);
        }

        return filename;
    }
    
    protected static String sanitizeString(String dirtyString) {
        StringBuilder sb = new StringBuilder(dirtyString.length());
        for (int i=0; i<dirtyString.length(); i++) {
            if(!Character.isIdentifierIgnorable(dirtyString.charAt(i))) {
                sb.append(dirtyString.charAt(i));
            }
        }
        return sb.toString();
    }

}