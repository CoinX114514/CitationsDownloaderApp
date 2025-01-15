package com.example.citationdownloader.downloaders;

import com.example.citationdownloader.models.PDFLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloader {
    private static final Logger logger = LoggerFactory.getLogger(FileDownloader.class);
    private static final String DOWNLOAD_DIR = "downloads";

    public FileDownloader() {
        // Create downloads directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(DOWNLOAD_DIR));
        } catch (IOException e) {
            logger.error("Failed to create downloads directory", e);
        }
    }

    public void processLink(PDFLink link) {
        String url = link.getUrl();
        
        if (isPdfUrl(url)) {
            downloadPdf(link);
        } else {
            openInBrowser(url);
        }
    }

    private void downloadPdf(PDFLink link) {
        String fileName = generateFileName(link);
        String savePath = Paths.get(DOWNLOAD_DIR, fileName).toString();
        
        try {
            URL url = new URL(link.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(savePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                
                link.setDownloaded(true);
                link.setLocalPath(savePath);
                logger.info("Downloaded PDF: {}", savePath);
            }
        } catch (Exception e) {
            logger.error("Failed to download PDF from URL: {}", link.getUrl(), e);
        }
    }

    private void openInBrowser(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
            logger.info("Opened URL in browser: {}", url);
        } catch (Exception e) {
            logger.error("Failed to open URL in browser: {}", url, e);
        }
    }

    private boolean isPdfUrl(String url) {
        return url.toLowerCase().endsWith(".pdf") || 
               url.toLowerCase().contains("pdf") ||
               url.toLowerCase().contains("article");
    }

    private String generateFileName(PDFLink link) {
        // Create a filename based on page number and URL
        String baseUrl = link.getUrl().replaceAll("[^a-zA-Z0-9.-]", "_");
        return String.format("page%d_%s.pdf", link.getPageNumber(), baseUrl);
    }
} 