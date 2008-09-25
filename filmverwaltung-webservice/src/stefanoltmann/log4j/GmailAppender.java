package stefanoltmann.log4j;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.spi.LoggingEvent;

public class GmailAppender extends SMTPAppender {

	public void append(LoggingEvent event) {

		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(getLayout().format(event));
			
			if (event.getThrowableInformation() != null) {
				String[] stackTrace = event.getThrowableInformation().getThrowableStrRep();
				for (int i = 0; i < stackTrace.length; i++)
					builder.append(stackTrace[i] + "\n");
			} else {
				builder.append(event.getMessage());
			}

			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.starttls.enable", "true");
//			props.setProperty("mail.smtp.debug", "true");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");

			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("stepsi@gmail.com", "");
				}
			};
			
			Session session = Session.getInstance(props, auth);
//			session.setDebug(true);

			Transport transport = session.getTransport();

			MimeMessage message = new MimeMessage(session);
			message.setSubject("Filmverwaltung Server Log4j Meldung");
			message.setContent(builder.toString(), "text/plain");
			message.setFrom(new InternetAddress("stepsi@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("stefan.oltmann@gmail.com"));

			transport.connect();
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
