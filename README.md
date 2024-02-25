
# JayoMail - Open Source Email Sending Utility ✉️

JayoMail is an open-source Java utility for sending emails with different email providers. It provides a flexible architecture supporting various email protocols and a convenient interface for configuring and sending emails.

## Features 🚀

- **Modular Architecture:** Choose from different email protocols like SMTP, IMAP, etc.
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
- Apache Maven

## Usage 🖥️

```java
// Your Java class

import io.github.roshangedam.jayomail.core.*;
import io.github.roshangedam.jayomail.strategy.*;
import io.github.roshangedam.jayomail.template.*;

public class Main {
    public static void main(String[] args) {
        // Create an email builder object
        EmailBuilder builder = new EmailBuilder()
            .setProtocol(MailProtocol.SMTP)  
            .setSender("sender@example.com")
            .setPassword("SenderPassword");

        // Build the email object
        Email email = builder.build();

        // Set email subject
        email.setSubject("Mail from JayoMail");

        // Set email body from HTML template
        email.setBodyFromTemplate(new HTMLTemplate()
                                    .setFilePath("jayomail/template/test.html")
                                    .setValue("greeting", "Hello JayoMail")
                                    .setValue("from", "JayoMail Developer"));

        // Attach an image file
        email.attachFile("jayomail/images/icon.png");

        // Set recipient email address
        email.setRecipient("recipient@example.com");

        // Send the email
        EmailResponse response = email.send();
        
        // Displaying the response details
        System.out.println("Status: " + response.getStatus());
        System.out.println("From: " + response.getFrom());
        System.out.println("To: " + response.getTo());
        System.out.println("Subject: " + response.getSubject());
        System.out.println("Timestamp: " + response.getTimestamp());

        // If there was a failure, print the error message
        if (response.getStatus().equals("Failure")) {
            System.out.println("Error Message: " + response.getErrorMessage());
        }
    }
}
```

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

Make sure to replace the placeholder values such as `sender@example.com`, `SenderPassword`, `recipient@example.com`, and file paths with actual values or variables as needed. Additionally, customize the template file paths and image file paths according to your project structure.