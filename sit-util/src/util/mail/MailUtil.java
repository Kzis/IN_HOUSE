package util.mail;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import util.mail.domain.MailErrorType;
import util.mail.domain.MailSecurityType;
import util.mail.exception.MailConnectErrorException;


public class MailUtil {
	public static boolean checkConnect(String protocal,String mailServer,String emailAddress
			,String password,int port, MailSecurityType.SecType secType) throws Exception{
		boolean result = false;
		
		try{
			Properties properties = System.getProperties();
			if(secType == null){
				properties.put("mail.smtp.port", port);
				properties.put("mail.smtp.auth", true);
			}else{
				properties.put("mail.smtp.port", port);
				properties.put("mail.smtp.auth", true);
				properties.put("mail.smtp.ssl.trust", "*");
				properties.put("mail.smtp.ssl.enable",true);
				if(secType.equals(MailSecurityType.SecType.TLS_SSL)){
					properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				}
			}
			properties.put("mail.host", mailServer);
			properties.put("mail.smtp.timeout", "6000");
			properties.put("mail.smtp.connectiontimeout", "6000");

			Session session = null;
			Authenticator authenticator = new SMTPAuthenticator(emailAddress, password);
			session = Session.getInstance(properties, authenticator);
			session.setDebug(true);

			if(secType != null && secType.equals(MailSecurityType.SecType.STARTTLS)){
				properties.put("mail.smtp.starttls.enable", true);
			}
			
			Transport t = session.getTransport(protocal);
			t.connect(mailServer, emailAddress, password);
			System.out.println("Connect Success");
			
			result = true;
			
			//t.sendMessage(mimemessage, mimemessage.getAllRecipients());
			t.close();
		}catch(AuthenticationFailedException e){
			System.out.println(e);
			throw new MailConnectErrorException(MailErrorType.Message.AUTH_FAIL.getValue());		
		}catch(MessagingException e){
			System.out.println(e);
			throw new MailConnectErrorException(MailErrorType.Message.CONNECT_FAIL.getValue());
		}catch(Exception e){ 
			throw e;
		}
		
		return result;
		
	}
	
	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		String username;
		String password;

		private SMTPAuthenticator(String authenticationUser, String authenticationPassword) {
			username = authenticationUser;
			password = authenticationPassword;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

}
