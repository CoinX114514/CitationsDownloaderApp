package com.example.citationdownloader.parsers;

import com.example.citationdownloader.models.PDFLink;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFHyperlinkExtractor {
    private static final Logger logger = LoggerFactory.getLogger(PDFHyperlinkExtractor.class);

    public List<PDFLink> extractLinks(String pdfPath) {
        List<PDFLink> links = new ArrayList<>();
        
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            int pageNumber = 0;
            
            for (var page : document.getPages()) {
                pageNumber++;
                List<PDAnnotation> annotations = page.getAnnotations();
                
                for (PDAnnotation annotation : annotations) {
                    if (annotation instanceof PDAnnotationLink link) {
                        if (link.getAction() instanceof PDActionURI action) {
                            String url = action.getURI();
                            // Get the text near the link if possible
                            String linkText = extractLinkText((PDPage)page, link);
                            links.add(new PDFLink(url, pageNumber, linkText));
                            logger.info("Found link on page {}: {}", pageNumber, url);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error extracting links from PDF: {}", pdfPath, e);
        }
        
        return links;
    }

    private String extractLinkText(PDPage page, PDAnnotationLink link) {
        try {
            PDRectangle pdRectangle = link.getRectangle();
            // Convert PDRectangle to Rectangle for text extraction
            Rectangle2D.Float rect = (Rectangle2D.Float) pdRectangle.toGeneralPath().getBounds2D();
            Rectangle rectangle = new Rectangle(
                (int) rect.getX(),
                (int) rect.getY(),
                (int) rect.getWidth(),
                (int) rect.getHeight()
            );

            // Extract text from the area around the link
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.addRegion("linkRegion", rectangle);
            stripper.extractRegions(page);
            
            String text = stripper.getTextForRegion("linkRegion").trim();
            return text.isEmpty() ? "Link at " + rectangle : text;
        } catch (Exception e) {
            logger.warn("Could not extract link text", e);
            return "";
        }
    }
} 