package com.example.citationdownloader.downloaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrieves citation metadata using the CrossRef API.
 */
public class APIDownloader {
  private static final String CROSSREF_API_URL = "https://api.crossref.org/works?query=";
  private static final OkHttpClient client = new OkHttpClient();

  /**
   * Searches for citation metadata using the CrossRef API.
   *
   * @param query Citation query string (e.g., author, title).
   * @return Citation metadata as a JSON string.
   */
  public static String searchMetadata(String query) {
    String url = CROSSREF_API_URL + query.replace(" ", "+");

    Request request = new Request.Builder()
            .url(url)
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful() && response.body() != null) {
        return response.body().string();
      } else {
        System.err.println("API request failed: " + response.message());
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    String query = "Einstein, 1905"; // Example query
    String jsonResponse = searchMetadata(query);

    if (jsonResponse != null) {
      // Parse JSON response
      JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
      System.out.println("API Response: " + jsonObject);
    }
  }
}