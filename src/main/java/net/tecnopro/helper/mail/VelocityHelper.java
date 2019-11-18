package net.tecnopro.helper.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;


public  class VelocityHelper {

	public static String getRendererMessage(String message, Map values) throws Exception{
		
		Properties p = new Properties();
	    p.setProperty("file.resource.loader.path", "/tmp");

		/*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        /*  next, get the Template  */
        String templateFileName = createTemporaryFileTemplate(message);
        Template t = ve.getTemplate(templateFileName);
        /*  create a context and add data */
        VelocityContext context = new VelocityContext(values);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
      
        String rendererMessage = writer.toString();
        /* show the World */
        System.out.println("VelocityHelper: Se renderizó el mensaje: " + rendererMessage );
        //destroyTemporaryFileTemplate(templateFileName);
        return rendererMessage;
		
	}
	
	public static String getRendererMessage(String message, Map values, String templateFileName) throws Exception{
		
		Properties p = new Properties();
	    p.setProperty("file.resource.loader.path", "/tmp");

		/*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        /*  next, get the Template  */
        Template t = ve.getTemplate(templateFileName);
        /*  create a context and add data */
        VelocityContext context = new VelocityContext(values);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
      
        String rendererMessage = writer.toString();
        /* show the World */
        //System.out.println("VelocityHelper: Se renderizó el mensaje: " + rendererMessage );
        return rendererMessage;
		
	}
	
	public static String createTemporaryFileTemplate(String message) throws IOException{
		File f=new File("/tmp");
		File tmp = File.createTempFile("email_", ".vm",f);
		if (tmp.canWrite()){
			FileOutputStream stream = new FileOutputStream(tmp);
			stream.write(message.getBytes());
			stream.close();
			System.out.println("VelocityHelper: se creo el archivo plantilla temporal: " + tmp.getAbsolutePath());
		} else {
			return "failure";
		}
		return tmp.getName();
	}
	
	public static boolean destroyTemporaryFileTemplate(String filename) throws IOException{
		
		File tmp = new File( System.getProperty("java.io.tmpdir") + File.separator + filename);
		if (tmp.canWrite()){
			return tmp.delete();
		} 
		return false;
	}
	
}
