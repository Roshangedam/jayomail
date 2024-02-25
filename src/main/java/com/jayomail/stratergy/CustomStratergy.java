package com.jayomail.stratergy;

import java.util.List;
import java.util.Map;

public  class CustomStratergy extends EmailStratergy {
    public CustomStratergy(String smtpServer, int smtpPort) {
        super(smtpServer, smtpPort);
    }

    @Override
    public Map<String, String> sendEmail(String senderEmail, String appPassword, String recipientEmail, String subject, String body, String htmlContent, List<String> attachments) {
        return send(senderEmail, appPassword, recipientEmail, subject, body, htmlContent, attachments);
    }
}