package com.example.citationdownloader.parsers;

import com.example.citationdownloader.downloaders.FileDownloader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;

import java.io.File;
import java.util.List;

/**
 * Extracts hyperlinks from a PDF file and optionally downloads the linked content.
 */
public class PDFHyperlinkExtractor {

  /**
   * Main method to run the extractor.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    // Path to the input PDF file
    String pdfPath = "example.pdf"; // Replace with the actual path to your PDF file

    // Attempt to load the PDF and extract hyperlinks
    try (PDDocument document = PDDocument.load(new File(pdfPath))) {
      int pageNumber = 0; // Counter for the current page number

      // Iterate through each page in the PDF document
      for (var page : document.getPages()) {
        pageNumber++;
        List<PDAnnotation> annotations = page.getAnnotations();

        // Process annotations on the page to find links
        for (PDAnnotation annotation : annotations) {
          if (annotation instanceof PDAnnotationLink link) {
            if (link.getAction() instanceof PDActionURI action) {
              // Extract the URI from the link
              String url = action.getURI();
              System.out.println("Page " + pageNumber + ": " + url);

              // Download the linked content (if applicable)
              if (url.startsWith("http")) {
                String savePath = "downloads/page" + pageNumber + "_file.pdf";
                FileDownloader.download(url, savePath);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      // Log the error if PDF processing fails
      e.printStackTrace();
    }
  }
}