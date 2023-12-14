package com.hotelco.utilities;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hotelco.developer.Settings;
import javafx.concurrent.Task;

/**
 * Utility class to send emails
 * @author John Lee
 */
public class SendMail
{
	Session newSession = null;
	MimeMessage mimeMessage = null;
	/**
	 * The main method to send an email by taking in a String email, subject, and body, and setting up
	 * a Google stmp server
	 * @param emailString
	 * @param subject
	 * @param body
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void startSend(String emailString, String subject, String body) {
		if(Settings.CAN_EMAIL){
			try {
				Task<Void> task = new Task<Void>() {
        	    @Override
            	protected Void call() throws Exception {
					String mailBody = body + EmailGenerator.SIGNATURE;
					SendMail mail = new SendMail();
					mail.setupServerProperties();
					mail.draftEmail(emailString, subject, mailBody); //(Email, EmailSubject, EmailBody)
					mail.sendEmail();					
					return null;
            	}  
        	};
        		new Thread(task).start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The method that inputs the login information to access an email account
	 * @throws MessagingException
	 */
	private void sendEmail() {
		try {
			/**the string you should use */
			String fromUser = "hotelcodesk@gmail.com";  //Enter sender email id
			String fromUserPassword = "bhyt bqgl fwbd tpav";  //Enter sender gmail password , this will be authenticated by gmail smtp server
			String emailHost = "smtp.gmail.com";
			Transport transport = newSession.getTransport("smtp");
			transport.connect(emailHost, fromUser, fromUserPassword);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
			System.out.println("\nEmail successfully sent!\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Add user email and info for room
	/**
	 * Sets up a draft email using a string pass for a email, subject, and body
	 * @param Email
	 * @param EmailSubject
	 * @param EmailBody
	 * @return a mime message
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	private MimeMessage draftEmail(String Email, String EmailSubject, String EmailBody) throws AddressException, MessagingException, IOException {
		String emailReceipient = Email;  //Enter list of email recepients
		String emailSubject = EmailSubject;
		mimeMessage = new MimeMessage(newSession);
		 

		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));

		mimeMessage.setSubject(emailSubject);
		mimeMessage.setText(EmailBody);
		
		 return mimeMessage;
	}

	/**
	 * Sets up the stmp server for access to send emails
	 */
	private void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties,null);
	}
	
}