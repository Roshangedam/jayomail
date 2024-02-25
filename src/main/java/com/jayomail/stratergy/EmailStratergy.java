package com.jayomail.stratergy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public abstract class EmailStratergy {
	protected String smtpServer;
	protected int smtpPort;

	public EmailStratergy(String smtpServer, int smtpPort) {
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
	}

	public abstract Map<String, String> sendEmail(String senderEmail, String appPassword, String recipientEmail,
			String subject, String body, String htmlContent, List<String> attachments);

	protected Map<String, String> send(String senderEmail, String appPassword, String recipientEmail, String subject,
			String body, String htmlContent, List<String> attachments) {
		Map<String, String> response = new HashMap<>();

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props, null);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(senderEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			msg.setSubject(subject);

			Multipart multipart = new MimeMultipart();
			if (body != null) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(body, "text/plain");
				multipart.addBodyPart(messageBodyPart);
			}

			if (htmlContent != null) {
				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
			}

			if (attachments != null) {
				for (String attachment : attachments) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName(new File(attachment).getName());
					multipart.addBodyPart(attachmentPart);
				}
			}

			msg.setContent(multipart);
			Transport.send(msg, senderEmail, appPassword);

			// Populate response map with information for success
										
			response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			response.put("smtp_server", smtpServer);
			response.put("smtp_port", String.valueOf(smtpPort));
			response.put("subject", subject);
			response.put("to", recipientEmail);
			response.put("from", senderEmail);
			response.put("status", "Success");

			// System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
			// Populate response map with information for failure
			response.put("status", "Failure");
			response.put("from", senderEmail);
			response.put("to", recipientEmail);
			response.put("subject", subject);
			response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			response.put("smtp_server", smtpServer);
			response.put("smtp_port", String.valueOf(smtpPort));
			response.put("error_message", e.getMessage()); // Include error message in response
		}

		return response;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

}
