package com.example.citationdownloader.models;

public class PDFLink {
    private String url;
    private int pageNumber;
    private String linkText;
    private boolean isDownloaded;
    private String localPath;

    public PDFLink(String url, int pageNumber, String linkText) {
        this.url = url;
        this.pageNumber = pageNumber;
        this.linkText = linkText;
        this.isDownloaded = false;
    }

    // Getters and setters
    public String getUrl() { return url; }
    public int getPageNumber() { return pageNumber; }
    public String getLinkText() { return linkText; }
    public boolean isDownloaded() { return isDownloaded; }
    public void setDownloaded(boolean downloaded) { isDownloaded = downloaded; }
    public String getLocalPath() { return localPath; }
    public void setLocalPath(String localPath) { this.localPath = localPath; }

    @Override
    public String toString() {
        return String.format("Page %d: %s (%s)", pageNumber, linkText, url);
    }
} 