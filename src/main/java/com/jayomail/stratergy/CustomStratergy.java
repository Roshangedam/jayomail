package com.jayomail.stratergy;

import java.util.List;
import java.util.Map;

import com.jayomail.core.EmailResponse;
import com.jayomail.enums.MailProtocol;

/**
 * A custom email strategy class for handling emails based on user-defined settings.
 */
public class CustomStratergy extends EmailStratergy {

    /**
     * Constructs a custom email strategy with the provided email server, port, protocol, and additional properties.
     *
     * @param emailServer           The email server address.
     * @param emailPort             The email server port.
     * @param emailProtocol         The email protocol (SMTP, POP, IMAP, etc.).
     * @param additionalProperties Additional properties for configuring the email server (optional).
     */
    public CustomStratergy(String emailServer, int emailPort, MailProtocol emailProtocol, Map<String, String> additionalProperties) {
        super(emailServer, emailPort, emailProtocol, additionalProperties);
    }

    /**
     * Constructs a custom email strategy with the provided email server, port, and protocol.
     *
     * @param emailServer     The email server address.
     * @param emailPort       The email server port.
     * @param emailProtocol   The email protocol (SMTP, POP, IMAP, etc.).
     */
    public CustomStratergy(String emailServer, int emailPort, MailProtocol emailProtocol) {
        super(emailServer, emailPort, emailProtocol);
    }

    /**
     * Constructs a custom email strategy with the provided email server, port, and SMTP protocol.
     *
     * @param emailServer     The email server address.
     * @param emailPort       The email server port.     
     */
    public CustomStratergy(String emailServer, int emailPort) {
        super(emailServer, emailPort, MailProtocol.SMTP);
    }
    
    /**
     * Sends an email using the custom email strategy.
     *
     * @param senderEmail    The sender's email address.
     * @param appPassword    The application password or authentication token.
     * @param recipientEmail The recipient's email address.
     * @param subject        The email subject.
     * @param body           The email body.
     * @param htmlContent    The HTML content of the email (optional).
     * @param attachments    The list of file paths to be attached (optional).
     * @return A EmailResponse containing information about the email sending status.
     */
    @Override
    public EmailResponse sendEmail(String senderEmail, String appPassword, String recipientEmail, String subject, String body, String htmlContent, List<String> attachments) {
        // Delegate the email sending to the superclass method
        return send(senderEmail, appPassword, recipientEmail, subject, body, htmlContent, attachments);
    }
}
