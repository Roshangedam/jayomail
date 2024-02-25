package com.jayomail.core;

import java.util.Map;

import com.jayomail.stratergy.EmailStratergy;
import com.jayomail.template.Template;
/**
 * The {@code Email} interface represents an email that can be composed, configured, and sent using different strategies.
 * Implementations of this interface provide methods to set various email attributes such as sender, recipient, subject,
 * body, attachments, etc., and to send the email using a specified strategy.
 */
public interface Email {
    /**
     * Sets the email strategy to be used for sending the email.
     *
     * @param strategy The email strategy to use.
     * @return The updated Email instance.
     */
    Email setStrategy(EmailStratergy strategy);

    /**
     * Sets the sender's email address.
     *
     * @param senderEmail The sender's email address.
     * @return The updated Email instance.
     */
    Email setSenderEmail(String senderEmail);

    /**
     * Sets the sender's email password or application-specific password.
     *
     * @param appPassword The sender's email password.
     * @return The updated Email instance.
     */
    Email setSenderPassword(String appPassword);

    /**
     * Sets the recipient's email address.
     *
     * @param recipientEmail The recipient's email address.
     */
    void setRecipientEmail(String recipientEmail);

    /**
     * Sets the subject of the email.
     *
     * @param subject The subject of the email.
     * @return The updated Email instance.
     */
    Email setSubject(String subject);

    /**
     * Sets the body content of the email.
     *
     * @param body The body content of the email.
     * @return The updated Email instance.
     */
    Email setBody(String body);

    /**
     * Sets the maximum attachment size allowed for the email.
     *
     * @param maxAttachmentSizeMb The maximum attachment size in megabytes.
     */
    void setMaxAttachmentSize(long maxAttachmentSizeMb);

    /**
     * Sets the body content of the email from a template.
     *
     * @param template The template to use for generating the email body.
     * @return The updated Email instance.
     */
    Email setBodyFromTemplate(Template template);

    /**
     * Attaches a file to the email.
     *
     * @param fileSource The path or URL of the file to attach.
     */
    void attachFile(String fileSource);

    /**
     * Sends the email using the configured strategy.
     *
     * @return A map containing information about the sending status.
     */
    Map<String, String> send();

    /**
     * Clears the content of the email (subject, body, attachments).
     */
    void clear();

    /**
     * Builds the email.
     *
     * @return The built Email instance.
     */
    EmailBuilder build();

    /**
     * Clears all attachments attached to the email.
     */
    void clearAttachments();
}
