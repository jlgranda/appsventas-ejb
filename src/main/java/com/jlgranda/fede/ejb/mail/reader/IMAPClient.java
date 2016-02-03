/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlgranda.fede.ejb.mail.reader;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author jlgranda
 */
public class IMAPClient {

    private Session session = null;
    private Store store = null;
    private String host = null;
    private String userName = null;
    private String password = null;

    public IMAPClient(String host, String userName, String password) {
        this.host = host;
        this.userName = userName;
        this.password = password;
    }

    public Message[] getMessages(String folderName, boolean debug) throws MessagingException {
        
        Properties prop = new Properties();
        prop.put("mail.imap.ssl.checkserveridentity", "false");
        prop.put("mail.imaps.ssl.trust", "*");
        prop.put("mail.mime.encodeparameters", "false"); 
        prop.put("mail.mime.encodefilename", "true"); 
        
        System.setProperty("mail.mime.encodeparameters", "false"); 
        System.setProperty("mail.mime.encodefilename", "true"); 
        
        session = Session.getDefaultInstance(prop);

        //session = Session.getDefaultInstance(System.getProperties(), null);
        session.setDebug(debug);
        store = session.getStore("imaps");
        store.connect(this.host, this.userName, this.password);
        //System.out.println("get default folder ..");
        Folder folder = store.getDefaultFolder();
        folder = folder.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }
    
    public void close() throws MessagingException{
        store.close();
    }
}
