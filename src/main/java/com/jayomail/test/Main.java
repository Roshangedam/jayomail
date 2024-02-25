package com.jayomail.test;
import java.util.HashMap;
import java.util.Map;

import com.jayomail.core.Email;
import com.jayomail.core.EmailBuilder;
import com.jayomail.core.EmailResponse;
import com.jayomail.enums.MailProtocol;
import com.jayomail.stratergy.CustomStratergy;
import com.jayomail.stratergy.GmailStratergy;
import com.jayomail.stratergy.IceWarpStratergy;
import com.jayomail.template.HTMLTemplate;

public class Main {
    public static void main(String[] args) {    	
    	Email roshan = new EmailBuilder()
            .setStrategy(new IceWarpStratergy())
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
        roshan.attachFile("https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg");
        roshan.setRecipientEmail("roshangedam1998@gmail.com");
        EmailResponse response = roshan.send();
        
        // Displaying the response details
        System.out.println(response);               
    }
}
