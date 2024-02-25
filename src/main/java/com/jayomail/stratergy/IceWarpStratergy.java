package com.jayomail.stratergy;

import java.util.List;
import java.util.Map;

public class IceWarpStratergy extends EmailStratergy {
    public IceWarpStratergy() {
        super("mail.microproindia.com", 587);
    }

    @Override
    public Map<String, String> sendEmail(String senderEmail, String appPassword, String recipientEmail, String subject, String body, String htmlContent, List<String> attachments) {
        return send(senderEmail, appPassword, recipientEmail, subject, body, htmlContent, attachments);
    }
}