package com.jayomail.stratergy;

import java.util.List;
import java.util.Map;

import com.jayomail.enums.MailProtocol;

/**
 * A strategy class for handling Gmail email configuration.
 */
public class GmailStratergy extends EmailStratergy {

    /**
     * Default constructor for Gmail SMTP.
     * Uses predefined settings for Gmail SMTP.
     */
    public GmailStratergy() {
        super(getServerAddress(MailProtocol.SMTP), getPort(MailProtocol.SMTP), MailProtocol.SMTP);
    }
	
    /**
     * Constructor for Gmail with specified protocol.
     *
     * @param protocol The email protocol to use (SMTP, POP, or IMAP).
     */
    public GmailStratergy(MailProtocol protocol) {
        super(getServerAddress(protocol), getPort(protocol), protocol);
    }

    /**
     * Constructor for Gmail with specified protocol and additional properties.
     *
     * @param protocol             The email protocol to use (SMTP, POP, or IMAP).
     * @param additionalProperties Additional properties to be set.
     */
    public GmailStratergy(MailProtocol protocol, Map<String, String> additionalProperties) {
        super(getServerAddress(protocol), getPort(protocol), protocol, additionalProperties);
    }

    /**
     * Gets the server address based on the protocol.
     *
     * @param protocol The email protocol.
     * @return The server address.
     * @throws IllegalArgumentException if the protocol is unsupported.
     */
    private static String getServerAddress(MailProtocol protocol) {
        switch (protocol) {
            case SMTP:
                return "smtp.gmail.com";
            case POP:
                return "pop.gmail.com";
            case IMAP:
                return "imap.gmail.com";
            default:
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
        }
    }

    /**
     * Gets the port based on the protocol.
     *
     * @param protocol The email protocol.
     * @return The port number.
     * @throws IllegalArgumentException if the protocol is unsupported.
     */
    private static int getPort(MailProtocol protocol) {
        switch (protocol) {
            case SMTP:
                return 587; // SMTP port
            case POP:
                return 995; // POP3 port
            case IMAP:
                return 993; // IMAP port
            default:
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
        }
    }

    /**
     * Sends an email using Gmail settings.
     *
     * @param senderEmail    The sender's email address.
     * @param appPassword    The application password or authentication token.
     * @param recipientEmail The recipient's email address.
     * @param subject        The email subject.
     * @param body           The email body.
     * @param htmlContent    The HTML content of the email (optional).
     * @param attachments    The list of file paths to be attached (optional).
     * @return A map containing information about the email sending status.
     */
    @Override
    public Map<String, String> sendEmail(String senderEmail, String appPassword, String recipientEmail, String subject, String body, String htmlContent, List<String> attachments) {
        return send(senderEmail, appPassword, recipientEmail, subject, body, htmlContent, attachments);
    }
}
