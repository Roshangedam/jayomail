package com.jayomail.core;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String getFileTypeFromUrl(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpGet)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IOException("Failed to fetch content type. Response code: " + statusCode);
            }

            String contentType = response.getEntity().getContentType().getValue();
            return getFileExtension(contentType);
        }
    }

    public static File downloadFile(String fileSource, long maxAttachmentSizeBytes) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(fileSource);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpGet)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IOException("Failed to download file. Response code: " + statusCode);
            }

            String contentType = response.getEntity().getContentType().getValue();
            String fileExtension = getFileExtension(contentType);

            // Generate timestamp
            String timestamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());

            String fileName;

            // If the URL contains a name, extract it; otherwise, generate a name based on the content type
            String[] parts = fileSource.split("/");
            if (parts.length > 0) {
                String lastPart = parts[parts.length - 1];
                if (!lastPart.isEmpty()) {
                    int dotIndex = lastPart.lastIndexOf('.');
                    if (dotIndex != -1) {
                        fileName = lastPart.substring(0, dotIndex) + "_" + timestamp + fileExtension;
                    } else {
                        fileName = lastPart + "_" + timestamp + fileExtension;
                    }
                } else {
                    fileName = "unknown_" + timestamp + fileExtension;
                }
            } else {
                fileName = "unknown_" + timestamp + fileExtension;
            }

            // Get the absolute path of the Maven resources directory
            String resourcesPath = Paths.get("src", "main", "resources").toAbsolutePath().toString();
            String tempFolder = Paths.get(resourcesPath, "temp").toString();
            Files.createDirectories(Paths.get(tempFolder));
            String filePath = Paths.get(tempFolder, fileName).toString();

            File outputFile = new File(filePath);
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                byte[] buffer = EntityUtils.toByteArray(response.getEntity());
                outputStream.write(buffer);

                if (outputFile.length() > maxAttachmentSizeBytes) {
                    throw new IOException("File size exceeds the maximum allowed size (" + maxAttachmentSizeBytes + " bytes).");
                }
            }

            return outputFile;
        }
    }

    private static String getFileExtension(String contentType) {
        if (contentType != null) {
            if (contentType.contains("application/pdf")) {
                return ".pdf";
            } else if (contentType.contains("image")) {
                return ".png";
            } else if (contentType.contains("video")) {
                return ".mp4";
            } else if (contentType.contains("audio")) {
                return ".mp3";
            }
        }
        return "";
    }

    public static long getFileSizeByPath(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try {
                return Files.size(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
