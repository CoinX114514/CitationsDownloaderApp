package com.example.citationdownloader.UI;

import com.example.citationdownloader.models.PDFLink;
import com.example.citationdownloader.parsers.PDFHyperlinkExtractor;
import com.example.citationdownloader.downloaders.FileDownloader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/**
 * Main UI class for the Citation Downloader application.
 * Handles the graphical user interface and user interactions.
 */
public final class MainUI extends Application {
    private TextArea resultsArea;
    private Label statusLabel;
    private FileDownloader fileDownloader;
    private PDFHyperlinkExtractor linkExtractor;
    private VBox linksBox;

    @Override
    public void start(@NotNull final Stage primaryStage) {
        initializeComponents();
        setupMainWindow(primaryStage);
    }

    /**
     * Initializes the main components of the application.
     */
    private void initializeComponents() {
        fileDownloader = new FileDownloader();
        linkExtractor = new PDFHyperlinkExtractor();
    }

    /**
     * Sets up the main window with tabs and content.
     *
     * @param primaryStage The primary stage for the application
     */
    private void setupMainWindow(@NotNull final Stage primaryStage) {
        try {
            TabPane tabPane = new TabPane();
            
            // Create and configure tabs
            Tab pdfTab = createTab("PDF Processing", createPDFProcessingView());
            Tab urlTab = createTab("URL Input", createURLInputView());
            Tab doiTab = createTab("DOI Input", createDOIInputView());
            
            tabPane.getTabs().addAll(pdfTab, urlTab, doiTab);

            Scene scene = new Scene(tabPane, 800, 600);
            primaryStage.setTitle("Citation Extractor");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a non-closable tab with the given title and content.
     *
     * @param title The title of the tab
     * @param content The content to display in the tab
     * @return The configured tab
     */
    @NotNull
    private Tab createTab(@NotNull final String title, @NotNull final VBox content) {
        Tab tab = new Tab(title);
        tab.setContent(content);
        tab.setClosable(false);
        return tab;
    }

    private VBox createPDFProcessingView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Create UI components
        Label titleLabel = new Label("PDF Citation Extractor");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button uploadButton = new Button("Upload PDF");
        statusLabel = new Label("No file selected");
        
        resultsArea = new TextArea();
        resultsArea.setPromptText("Results will appear here...");
        resultsArea.setPrefRowCount(5);
        resultsArea.setWrapText(true);
        resultsArea.setEditable(false);

        // Links section
        linksBox = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(linksBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(200);
        
        // Configure file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        // Handle file upload
        uploadButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                statusLabel.setText("Processing: " + file.getName());
                processFile(file);
            }
        });

        root.getChildren().addAll(
            titleLabel,
            uploadButton,
            statusLabel,
            resultsArea,
            new Label("Found Links:"),
            scrollPane
        );

        return root;
    }

    private VBox createURLInputView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("URL Processing");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField urlInput = new TextField();
        urlInput.setPromptText("Enter URL here...");
        urlInput.setPrefWidth(400);

        Button processButton = new Button("Process URL");
        TextArea urlResults = new TextArea();
        urlResults.setPromptText("URL processing results will appear here...");
        urlResults.setPrefRowCount(10);
        urlResults.setWrapText(true);
        urlResults.setEditable(false);

        processButton.setOnAction(e -> {
            String url = urlInput.getText().trim();
            if (!url.isEmpty()) {
                // TODO: Implement URL processing logic
                urlResults.setText("Processing URL: " + url);
            }
        });

        root.getChildren().addAll(
            titleLabel,
            new Label("Enter URL to process:"),
            urlInput,
            processButton,
            urlResults
        );

        return root;
    }

    private VBox createDOIInputView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("DOI Processing");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField doiInput = new TextField();
        doiInput.setPromptText("Enter DOI here (e.g., 10.1000/xyz123)");
        doiInput.setPrefWidth(400);

        Button processButton = new Button("Process DOI");
        TextArea doiResults = new TextArea();
        doiResults.setPromptText("DOI processing results will appear here...");
        doiResults.setPrefRowCount(10);
        doiResults.setWrapText(true);
        doiResults.setEditable(false);

        processButton.setOnAction(e -> {
            String doi = doiInput.getText().trim();
            if (!doi.isEmpty()) {
                // TODO: Implement DOI processing logic
                doiResults.setText("Processing DOI: " + doi);
            }
        });

        root.getChildren().addAll(
            titleLabel,
            new Label("Enter DOI to process:"),
            doiInput,
            processButton,
            doiResults
        );

        return root;
    }

    private void processFile(File file) {
        new Thread(() -> {
            try {
                List<PDFLink> links = linkExtractor.extractLinks(file.getAbsolutePath());
                
                Platform.runLater(() -> {
                    resultsArea.setText(String.format("Found %d links in %s\n", 
                        links.size(), file.getName()));
                    
                    linksBox.getChildren().clear();
                    
                    for (PDFLink link : links) {
                        HBox linkRow = createLinkRow(link);
                        linksBox.getChildren().add(linkRow);
                    }
                    
                    statusLabel.setText("Completed processing: " + file.getName());
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    resultsArea.setText("Error processing file: " + e.getMessage());
                    statusLabel.setText("Error processing file");
                });
            }
        }).start();
    }

    private HBox createLinkRow(PDFLink link) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(5));
        row.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");

        Label pageLabel = new Label("Page " + link.getPageNumber());
        pageLabel.setPrefWidth(60);
        
        Label urlLabel = new Label(link.getUrl());
        urlLabel.setWrapText(true);
        HBox.setHgrow(urlLabel, Priority.ALWAYS);
        
        Button openButton = new Button("Open");
        openButton.setOnAction(e -> {
            fileDownloader.processLink(link);
            openButton.setDisable(true);
            openButton.setText(link.isDownloaded() ? "Downloaded" : "Opened");
        });

        row.getChildren().addAll(pageLabel, urlLabel, openButton);
        return row;
    }

    public static void main(String[] args) {
        launch(args);
    }
} 