package com.example.citationdownloader.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a link found in a PDF document.
 * Contains information about the link's location, text, and download status.
 */
public final class PDFLink {
    @NotNull private final String url;
    private final int pageNumber;
    @NotNull private final String linkText;
    private boolean isDownloaded;
    @Nullable private String localPath;

    /**
     * Creates a new PDFLink instance.
     *
     * @param url The URL of the link
     * @param pageNumber The page number where the link was found
     * @param linkText The text associated with the link
     */
    public PDFLink(@NotNull final String url, final int pageNumber, @NotNull final String linkText) {
        this.url = url;
        this.pageNumber = pageNumber;
        this.linkText = linkText;
        this.isDownloaded = false;
    }

    @NotNull
    public String getUrl() { return url; }
    
    public int getPageNumber() { return pageNumber; }
    
    @NotNull
    public String getLinkText() { return linkText; }
    
    public boolean isDownloaded() { return isDownloaded; }
    
    public void setDownloaded(final boolean downloaded) { isDownloaded = downloaded; }
    
    @Nullable
    public String getLocalPath() { return localPath; }
    
    public void setLocalPath(@Nullable final String localPath) { this.localPath = localPath; }

    @Override
    @NotNull
    public String toString() {
        return String.format("Page %d: %s (%s)", pageNumber, linkText, url);
    }
} 