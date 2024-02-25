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

public class HTMLTemplate extends Template {
    public HTMLTemplate() {
        super();
    }

    @Override
    public HTMLTemplate setTemplate(String content) {
        this.content = content;
        return this;
    }

    @Override
    public HTMLTemplate setValue(String key, String value) {
        if (this.content != null) {
            this.content = this.content.replace("{{" + key + "}}", value);
        }
        return this;
    }

    @Override
    public String build() {
        return this.content;
    }
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

    @Override
    public String getContent() {
    	return this.content;
    }
}