package com.jayomail.test;
import java.util.Map;

import com.jayomail.core.EmailBuilder;
import com.jayomail.stratergy.CustomStratergy;
import com.jayomail.template.HTMLTemplate;

public class Main {
    public static void main(String[] args) {
    	EmailBuilder roshan = new EmailBuilder()
            .setStrategy(new CustomStratergy("mail.microproindia.com",587))
            .setSenderEmail("rgedam@microproindia.com")
            .setSenderPassword("Pass@1234")
            .build();

        roshan.setMaxAttachmentSize(10);
        roshan.setSubject("Mail from Jayomail");
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
