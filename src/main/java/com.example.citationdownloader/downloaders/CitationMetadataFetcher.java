package com.example.citationdownloader.downloaders;

import com.example.citationdownloader.parsers.CitationParser;

import java.util.List;

public class CitationMetadataFetcher {

  public static void main(String[] args) {
    String pdfPath = "example.pdf"; // Replace with your PDF path

    // Step 1: Extract citations from the PDF
    List<String> citations = CitationParser.extractCitations(pdfPath);

    System.out.println("Extracted Citations:");
    citations.forEach(System.out::println);

    // Step 2: Fetch metadata for each citation
    for (String citation : citations) {
      System.out.println("Fetching metadata for: " + citation);
      String metadata = APIDownloader.searchMetadata(citation);

      if (metadata != null) {
        System.out.println("Metadata: " + metadata);
      } else {
        System.out.println("No metadata found for: " + citation);
      }
    }
  }
}