package com.hotelco.utilities;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMail
{

	//SETUP MAIL SERVER PROPERTIES
	//DRAFT AN EMAIL
	//SEND EMAIL
		
	Session newSession = null;
	MimeMessage mimeMessage = null;
	public static void main(String[] args) throws AddressException, MessagingException, IOException
	{

		SendMail mail = new SendMail();
		mail.setupServerProperties();
		mail.draftEmail(args[0], args[1], args[2]); //(Email, EmailSubject, EmailBody)
		mail.sendEmail();
	}

	private void sendEmail() throws MessagingException {
		String fromUser = "hotelcoDesk@gmail.com";  //Enter sender email id
		String fromUserPassword = "Safepassword4!";  //Enter sender gmail password , this will be authenticated by gmail smtp server
		String emailHost = "smtp.gmail.com";
		Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
		System.out.println("Email successfully sent!!!");
	}

	//Add user email and info for room
	private MimeMessage draftEmail(String Email, String EmailSubject, String EmailBody) throws AddressException, MessagingException, IOException {
		String emailReceipient = Email;  //Enter list of email recepients
		String emailSubject = EmailSubject;
		String emailBody = EmailBody;
		mimeMessage = new MimeMessage(newSession);
		 

			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));

		mimeMessage.setSubject(emailSubject);
	   
      // CREATE MIMEMESSAGE 
	    // CREATE MESSAGE BODY PARTS 
	    // CREATE MESSAGE MULTIPART 
	    // ADD MESSAGE BODY PARTS ----> MULTIPART 
	    // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object 
	    
	    
		 MimeBodyPart bodyPart = new MimeBodyPart();
		 bodyPart.setContent(emailBody,"html/text");
		 MimeMultipart multiPart = new MimeMultipart();
		 multiPart.addBodyPart(bodyPart);
		 mimeMessage.setContent(multiPart);
		 return mimeMessage;
	}

	private void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "547");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties,null);
	}
	
}	
