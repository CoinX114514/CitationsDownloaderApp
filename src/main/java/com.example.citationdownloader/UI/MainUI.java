package com.example.citationdownloader.UI;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import javafx.geometry.Insets;

public class MainUI extends Application {

  @Override
  public void start(Stage primaryStage) {
    // Set up the main layout
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20));

    // Create a label for instructions
    Label instructionLabel = new Label("Upload a PDF to extract citations and hyperlinks:");

    // Create a button to upload a PDF
    Button uploadButton = new Button("Upload PDF");
    Label selectedFileLabel = new Label("No file selected");

    // File chooser to select PDF
    FileChooser fileChooser = new FileChooser();
    uploadButton.setOnAction(e -> {
      File selectedFile = fileChooser.showOpenDialog(primaryStage);
      if (selectedFile != null) {
        selectedFileLabel.setText("Selected File: " + selectedFile.getName());
        // Call the extraction logic here
        handleFileProcessing(selectedFile);
      } else {
        selectedFileLabel.setText("No file selected");
      }
    });

    // Create a text area to display results
    TextArea resultsArea = new TextArea();
    resultsArea.setEditable(false);
    resultsArea.setPromptText("Results will appear here...");

    // Add all components to the layout
    layout.getChildren().addAll(instructionLabel, uploadButton, selectedFileLabel, resultsArea);

    // Set up the stage and scene
    Scene scene = new Scene(layout, 600, 400);
    primaryStage.setTitle("Citation Extractor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Handles the file processing logic.
   * Calls citation and hyperlink extraction functionality.
   *
   * @param file The selected PDF file.
   */
  private void handleFileProcessing(File file) {
    // Extract hyperlinks (using PDFHyperlinkExtractor)
    System.out.println("Processing file: " + file.getAbsolutePath());
    // Here, integrate citation and hyperlink extraction
    // Example:
    // List<String> citations = CitationParser.extractCitations(file.getAbsolutePath());
    // Show results in the TextArea or log them
  }

  public static void main(String[] args) {
    launch(args);
  }

}