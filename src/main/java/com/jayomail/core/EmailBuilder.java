package com.jayomail.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jayomail.stratergy.CustomStratergy;
import com.jayomail.stratergy.EmailStratergy;
import com.jayomail.template.Template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class EmailBuilder {
    private EmailStratergy strategy;
    private String senderEmail;
    private String appPassword;
    private String recipientEmail;
    private String subject;
    private String body;
    private String htmlContent;
    private List<String> attachments = new ArrayList<>();
    private long maxAttachmentSizeBytes;

    public EmailBuilder(){}
    
    public EmailBuilder(long defaultMaxAttachmentSizeMb) {
        this.maxAttachmentSizeBytes = defaultMaxAttachmentSizeMb * 1024 * 1024; // Default: 10 MB
    }

    public void setMaxAttachmentSize(long maxAttachmentSizeMb) {
        this.maxAttachmentSizeBytes = maxAttachmentSizeMb * 1024 * 1024;
    }

    public void attachFile(String fileSource) {
        if (isURL(fileSource)) {
            try {
                File downloadedFile = FileUtil.downloadFile(fileSource,maxAttachmentSizeBytes);
                if (downloadedFile != null) {
                    attachments.add(downloadedFile.getAbsolutePath());
                } else {
                    System.out.println("Failed to attach file: file path is "+fileSource);
                }
            } catch (IOException e) {
            	e.printStackTrace();
                System.out.println("Failed to attach file:"+fileSource+" :" +" Error message: " + e.getMessage());
            }
        } else {
        	File file = new File(fileSource);
        	if (file.exists() && file.isFile()) {
        	    try {
        	        if (file.length() <= maxAttachmentSizeBytes) {
        	            attachments.add(file.getAbsolutePath());
        	        } else {
        	            System.out.println("Failed to attach file: "+fileSource+" :" +" File size exceeds the maximum allowed size.");
        	        }
        	    } catch (Exception e) {
        	        System.out.println("Failed to attach file: " +fileSource+" :" + e.getMessage());
        	        e.printStackTrace();
        	    }
        	} else {
        	    System.out.println("Failed to attach file: "+fileSource+" :" +" File does not exist or is not a valid file.");
        	}

        }
    }

    public Map<String, String> send() {
        Map<String, String> response = new HashMap<>();

        if (strategy != null) {
            try {
                response = strategy.sendEmail(senderEmail, appPassword, recipientEmail,
                        subject, body, htmlContent, attachments);
                response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                cleanTemp();
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                if (strategy instanceof CustomStratergy) {
                    errorMessage += " [Server: " + ((CustomStratergy) strategy).getSmtpServer() +
                            ", Port: " + ((CustomStratergy) strategy).getSmtpPort() + "]";
                }
                response.put("status", "Failure");
                response.put("error_message", errorMessage);
                response.put("from", senderEmail);
                response.put("to", recipientEmail);
                response.put("subject", subject);
                response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                cleanTemp();
            }
        } else {
            response.put("status", "Failure");
            response.put("error_message", "Strategy not set. Use setStrategy() to set the email strategy.");
            response.put("from", null);
            response.put("to", null);
            response.put("subject", null);
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }

        return response;
    }


    public EmailBuilder setStrategy(EmailStratergy strategy) {
        this.strategy = strategy;
        return this;
    }

    public EmailBuilder setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }

    public EmailBuilder setSenderPassword(String appPassword) {
        this.appPassword = appPassword;
        return this;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public EmailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder setBody(String body) {
        this.body = body;
        return this;
    }
    
    
    public EmailBuilder setBodyFromTemplate(Template template) {
        this.htmlContent = template.getContent();
        return this;
    }
    
    public EmailBuilder build() {
        return this;
    }

    
    private boolean isURL(String path) {
        try {
            new URL(path).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void cleanTemp() {
    	String resourcesPath = Paths.get("src", "main", "resources").toAbsolutePath().toString();
        String tempFolder = Paths.get(resourcesPath, "temp").toString();        
        for (String attachment : attachments) {
            try {
                File attachmentFile = new File(attachment);
                if (attachmentFile.getAbsolutePath().startsWith(tempFolder)) {
                    attachmentFile.delete();
                }
            } catch (Exception e) {
            	e.printStackTrace();
                System.out.println("Error removing file: " + attachment);
            }
        }
    }
    
    public void clear() {
        this.subject = "";
        this.body = "";
        this.htmlContent = "";
        this.attachments.clear();
    }
    
    public void clearAttachments() {
        this.attachments.clear();
    }
}

