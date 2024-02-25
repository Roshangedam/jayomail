package com.jayomail.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * HTMLTemplate class represents a template for generating HTML content.
 * It provides methods to set the template content, replace placeholder values,
 * load content from a URL or a file, and build the final HTML content.
 */
public class HTMLTemplate extends Template {

    /**
     * Default constructor.
     */
    public HTMLTemplate() {
        super();
    }

    /**
     * Sets the content of the HTML template.
     * @param content The HTML content to set as the template.
     * @return The HTMLTemplate object.
     */
    @Override
    public HTMLTemplate setTemplate(String content) {
        this.content = content;
        return this;
    }

    /**
     * Replaces a placeholder in the template with a specified value.
     * @param key The placeholder key to replace.
     * @param value The value to replace the placeholder with.
     * @return The HTMLTemplate object.
     */
    @Override
    public HTMLTemplate setValue(String key, String value) {
        if (this.content != null) {
            this.content = this.content.replace("{{" + key + "}}", value);
        }
        return this;
    }

    /**
     * Builds the final HTML content after replacing all placeholder values.
     * @return The final HTML content.
     */
    @Override
    public String build() {
        return this.content;
    }

    /**
     * Loads HTML content from a URL and sets it as the template content.
     * @param url The URL to fetch the HTML content from.
     * @return The HTMLTemplate object.
     */
    public HTMLTemplate loadFromUrl(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String contentType = response.getEntity().getContentType().getValue();
                if (contentType != null && contentType.startsWith("text/html")) {
                    String htmlContent = EntityUtils.toString(response.getEntity());
                    this.content = htmlContent;
                } else {
                    System.out.println("Template loading failure. Invalid content type: " + contentType);
                }
            } else {
                System.out.println("Template loading failure. Failed to fetch template from URL: " + url + " (Status code: " + statusCode + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Template loading failure. Error: " + e.getMessage());
        }
        return this;
    }

    /**
     * Loads HTML content from a file and sets it as the template content.
     * @param filePath The path to the HTML file.
     * @return The HTMLTemplate object.
     */
    public HTMLTemplate loadFromFile(String filePath) {
        try {
            File file;
            if (Paths.get(filePath).isAbsolute()) {
                // If the filePath is already absolute
                file = new File(filePath);
            } else {
                // If the filePath is relative
                String currentDir = System.getProperty("user.dir");
                String absolutePath = Paths.get(currentDir, filePath).toString();
                file = new File(absolutePath);
            }

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                this.content = sb.toString();
                reader.close();
            } else {
                System.out.println("Template loading failure. File does not exist: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Template loading failure. Error: " + e.getMessage());
        }
        return this;
    }

    /**
     * Retrieves the content of the HTML template.
     * @return The HTML content of the template.
     */
    @Override
    public String getContent() {
        return this.content;
    }
}
