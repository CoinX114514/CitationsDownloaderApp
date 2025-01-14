package com.example.citationdownloader.downloaders;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for downloading files from URLs.
 */
public class FileDownloader {
  // Logger instance for logging events
  private static final Logger logger = LoggerFactory.getLogger(FileDownloader.class);

  /**
   * Downloads a file from the specified URL and saves it to the given path.
   *
   * @param fileUrl  The URL of the file to download.
   * @param savePath The local file path where the downloaded file will be saved.
   */
  public static void download(String fileUrl, String savePath) {
    logger.info("Starting download: {}", fileUrl); // Log the start of the download
    try {
      // Open a connection to the URL
      URL url = new URL(fileUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Stream the file content to the specified save path
      try (InputStream in = connection.getInputStream();
           FileOutputStream out = new FileOutputStream(savePath)) {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
        logger.info("Downloaded successfully: {}", savePath); // Log successful download
      }
    } catch (Exception e) {
      // Log an error if the download fails
      logger.error("Failed to download file from URL: {}", fileUrl, e);
    }
  }
}