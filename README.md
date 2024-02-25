
# JayoMail - Open Source Email Sending Utility ✉️

JayoMail is an open-source Java utility for sending emails with different email providers. It provides a flexible architecture supporting various email protocols and a convenient interface for configuring and sending emails.

## Features 🚀

- **Modular Architecture:** Choose from different email strategies like Gmail, IceWarp, etc.
- **Builder Pattern:** Easily configure and customize emails using a builder pattern.
- **Template Support:** Support for email templates with dynamic values.
- **File Attachment:** Attach files from local paths or URLs.

## Getting Started 🛠️

Follow these instructions to get started with JayoMail in your Java project.

### Installation via Maven 📦

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.roshangedam</groupId>
    <artifactId>jayomail</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Prerequisites 📋

- Java Development Kit (JDK) 8 or higher

## Usage 🖥️

```java
package com.jayomail.test;

import com.jayomail.core.Email;
import com.jayomail.core.EmailBuilder;
import com.jayomail.core.EmailResponse;
import com.jayomail.stratergy.GmailStratergy;
import com.jayomail.template.HTMLTemplate;

public class Main {
    public static void main(String[] args) {    	
    	Email roshan = new EmailBuilder()
            .setStrategy(new GmailStratergy())
            .setSenderEmail("sender@example.com"")
            .setSenderPassword("SenderPassword")
            .build();

        roshan.setMaxAttachmentSize(10);
        roshan.setSubject("Mail from Jayomail ");
        roshan.setBody("");
        
        /*
	        html file to be like 
	        <div>  
	        		{{greeting}} This message is from
	        		    {{from}}
	        		    Thank you
	         </div>        
        */
        
        roshan.setBodyFromTemplate(new HTMLTemplate()
            .loadFromFile("src/main/resources/template/test.html")
            .setValue("greeting", "hello bholya")
            .setValue("from", "Jayomail developer")
        );
        
        
        roshan.attachFile("some/file/name");
        roshan.setRecipientEmail("recipient@example");
        EmailResponse response = roshan.send();
        
        // Displaying the response details
        System.out.println(response);               
    }
}
```

Make sure to replace placeholder values such as `rgedam@micoproindia.com`, `Pass@1234`, `roshangedam1998@gmail.com`, and file paths with actual values or variables as needed.

## Contributing 🤝

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License 📄

This project is licensed under the [License](LICENSE.md) - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments 🙌

- Hat tip to anyone whose code was used
- Inspiration
- etc.

This version of JayoMail supports email templates with dynamic values using the `HTMLTemplate` class. You can attach files from local paths or URLs using the `attachFile` method.

---