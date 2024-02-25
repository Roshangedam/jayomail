package com.jayomail.core;

import java.util.Map;

/**
 * Represents the response of an email sending operation.
 */
public class EmailResponse {
    private String timestamp;
    private String emailServer;
    private String emailProtocol;
    private String emailPort;
    private String subject;
    private String to;
    private String from;
    private String status;
    private String errorMessage;

    /**
     * Constructs an EmailResponse object from a response map.
     * @param response A map containing the response data.
     */
//    public EmailResponse(Map<String, String> response) {
//        this.timestamp = response.get("timestamp");
//        this.emailServer = response.get("email_server");
//        this.emailProtocol = response.get("email_protocol");
//        this.emailPort = response.get("email_port");
//        this.subject = response.get("subject");
//        this.to = response.get("to");
//        this.from = response.get("from");
//        this.status = response.get("status");
//        this.errorMessage = response.get("error_message");
//    }

    /**
     * Retrieves the timestamp when the email was sent.
     * @return The timestamp in the format "dd/MM/yyyy HH:mm".
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the email was sent.
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the email server used for sending the email.
     * @return The email server.
     */
    public String getEmailServer() {
        return emailServer;
    }

    /**
     * Sets the email server used for sending the email.
     * @param emailServer The email server to set.
     */
    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    /**
     * Retrieves the email protocol used for sending the email.
     * @return The email protocol.
     */
    public String getEmailProtocol() {
        return emailProtocol;
    }

    /**
     * Sets the email protocol used for sending the email.
     * @param emailProtocol The email protocol to set.
     */
    public void setEmailProtocol(String emailProtocol) {
        this.emailProtocol = emailProtocol;
    }

    /**
     * Retrieves the email port used for sending the email.
     * @return The email port.
     */
    public String getEmailPort() {
        return emailPort;
    }

    /**
     * Sets the email port used for sending the email.
     * @param emailPort The email port to set.
     */
    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    /**
     * Retrieves the subject of the email.
     * @return The email subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     * @param subject The email subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Retrieves the recipient email address.
     * @return The recipient email address.
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the recipient email address.
     * @param to The recipient email address to set.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Retrieves the sender email address.
     * @return The sender email address.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the sender email address.
     * @param from The sender email address to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Retrieves the status of the email sending operation.
     * @return The email sending status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the email sending operation.
     * @param status The email sending status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the error message if the email sending operation fails.
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message if the email sending operation fails.
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	@Override
	public String toString() {
		return "[\nstatus = " + status + "\nsubject = " + subject + "\nto = " + to + "\nfrom = " + from+"\ntimestamp = " + timestamp + "\nemailServer = " + emailServer + "\nemailProtocol = "
				+ emailProtocol + "\nemailPort = " + emailPort + "\nerrorMessage = " + errorMessage + "\n]";
	}
    
    
}
