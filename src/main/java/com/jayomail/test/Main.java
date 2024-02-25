package com.jayomail.test;
import java.util.HashMap;
import java.util.Map;

import com.jayomail.core.Email;
import com.jayomail.core.EmailBuilder;
import com.jayomail.enums.MailProtocol;
import com.jayomail.stratergy.CustomStratergy;
import com.jayomail.stratergy.GmailStratergy;
import com.jayomail.template.HTMLTemplate;

public class Main {
    public static void main(String[] args) {
    	Map<String, String> prop =new  HashMap<String, String>();
    	prop.put("mail.store.protocol", "imaps");
    	Email roshan = new EmailBuilder()
            .setStrategy(new GmailStratergy(MailProtocol.IMAP,prop))
            .setSenderEmail("rgedam@microproindia.com")
            .setSenderPassword("Pass@1234")
            .build();

        roshan.setMaxAttachmentSize(10);
        roshan.setSubject("Mail from pop ");
        roshan.setBody("");
        roshan.setBodyFromTemplate(new HTMLTemplate()
            .loadFromFile("src/main/resources/template/test.html")
            .setValue("greeting", "hello bholya")
            .setValue("from", "Jayomail developer")
        );
//        roshan.attachFile("https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg");
        roshan.setRecipientEmail("roshangedam1998@gmail.com");
        Map<String, String> res = roshan.send();

        System.out.println(res);
    }
}
