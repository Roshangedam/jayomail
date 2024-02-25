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

import com.jayomail.enums.MailProtocol;

public abstract class EmailStratergy {
	protected String emailServer;
    protected MailProtocol emailProtocol;
    protected int emailPort;
    protected Map<String, String> additionalProperties;

    public EmailStratergy(String emailServer, int emailPort, MailProtocol emailProtocol) {
        this.emailServer = emailServer;
        this.emailPort = emailPort;
        this.emailProtocol = emailProtocol;
        this.additionalProperties = new HashMap<String,String>();
        additionalProperties.put("mail.store.protocol", emailProtocol.toString());
        additionalProperties.put("mail." + emailProtocol.toString().toLowerCase() + ".host", emailServer);
        additionalProperties.put("mail." + emailProtocol.toString().toLowerCase() + ".port", String.valueOf(emailPort));
    }

    public EmailStratergy(String emailServer, int emailPort, MailProtocol emailProtocol, Map<String, String> additionalProperties) {
        this.emailServer = emailServer;
        this.emailPort = emailPort;
        this.emailProtocol = emailProtocol;
        this.additionalProperties = additionalProperties;
        // Ensure default properties are applied
        if (!additionalProperties.containsKey("mail.store.protocol")) {
            additionalProperties.put("mail.store.protocol", emailProtocol.toString());
        }
        if (!additionalProperties.containsKey("mail." + emailProtocol.toString().toLowerCase() + ".host")) {
            additionalProperties.put("mail." + emailProtocol.toString().toLowerCase() + ".host", emailServer);
        }
        if (!additionalProperties.containsKey("mail." + emailProtocol.toString().toLowerCase() + ".port")) {
            additionalProperties.put("mail." + emailProtocol.toString().toLowerCase() + ".port", String.valueOf(emailPort));
        }
    }
    protected Session createMailSession() {
        Properties props = new Properties();
        props.putAll(additionalProperties);
        return Session.getDefaultInstance(props);
    }
	public abstract Map<String, String> sendEmail(String senderEmail, String appPassword, String recipientEmail,
			String subject, String body, String htmlContent, List<String> attachments);

	protected Map<String, String> send(String senderEmail, String appPassword, String recipientEmail, String subject,
			String body, String htmlContent, List<String> attachments) {
		Map<String, String> response = new HashMap<>();

		Session session = createMailSession();

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
			response.put("email_server", emailServer);
			response.put("email_protocol", emailProtocol.toString());
			response.put("email_port", String.valueOf(emailPort));
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
			response.put("email_server", emailServer);
			response.put("email_protocol", emailProtocol.toString());
			response.put("email_port", String.valueOf(emailPort));			
			response.put("error_message", e.getMessage()); // Include error message in response
		}

		return response;
	}

	public String getEmailServer() {
		return emailServer;
	}

	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	public MailProtocol getEmailProtocol() {
		return emailProtocol;
	}

	public void setEmailProtocol(MailProtocol emailProtocol) {
		this.emailProtocol = emailProtocol;
	}

	public int getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(int emailPort) {
		this.emailPort = emailPort;
	}

	

}
