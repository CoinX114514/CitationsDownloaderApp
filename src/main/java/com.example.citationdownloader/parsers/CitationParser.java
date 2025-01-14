package com.example.citationdownloader.parsers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses textual citations from a PDF document.
 */
public class CitationParser {

  /**
   * Extracts citations from the specified PDF file.
   *
   * @param pdfPath Path to the PDF file.
   * @return A list of extracted citation strings.
   */
  public static List<String> extractCitations(String pdfPath) {
    List<String> citations = new ArrayList<>();

    try (PDDocument document = PDDocument.load(new File(pdfPath))) {
      PDFTextStripper textStripper = new PDFTextStripper();
      String text = textStripper.getText(document);

      // Define a regex pattern for APA-style citations (example)
      Pattern citationPattern = Pattern.compile(
              "\\(([^,]+), (\\d{4})\\)"); // Matches (Author, Year)

      Matcher matcher = citationPattern.matcher(text);

      // Extract all matches
      while (matcher.find()) {
        citations.add(matcher.group());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return citations;
  }

  public static void main(String[] args) {
    String pdfPath = "example.pdf"; // Replace with your PDF file path
    List<String> citations = extractCitations(pdfPath);

    // Print extracted citations
    System.out.println("Extracted Citations:");
    citations.forEach(System.out::println);
  }
}
