package com.zeroneroiv.mediumsearchengine.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroneroiv.mediumsearchengine.models.Article;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

public class DevToApiUtility {
    public void getTop20ArticlesByUsername(String username) {
        String url = "https://dev.to/api/articles?username=" + username;

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String jsonResponse = response.body();

                    // Parse the JSON response
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Article> articles = objectMapper.readValue(
                            jsonResponse,
                            objectMapper
                                    .getTypeFactory()
                                    .constructCollectionType(List.class, Article.class)
                    );

                    // Sort articles by popularity (positive reactions count)
                    articles.sort(Comparator.comparingInt(Article::getPositiveReactionsCount).reversed());

                    // Get the top 20 articles
                    List<Article> top20Articles = articles.stream()
                            .limit(20)
                            .toList();

                    System.out.println("Top 20 Articles by " + username + ":");
                    for (Article article : top20Articles) {
                        System.out.println("Title: " + article.getTitle());
                        System.out.println("URL: " + article.getUrl());
                        System.out.println("Positive Reactions: " + article.getPositiveReactionsCount());
                        System.out.println("Published At: " + article.getPublishedAt());
                        System.out.println();
                    }
                } else {
                    System.out.println("Failed to fetch articles. Status code: " + response.statusCode());
                }
            } catch (UncheckedIOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
