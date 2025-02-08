package com.projectbluemoon.devtosearchengine.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectbluemoon.devtosearchengine.models.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DevToApiUtility {
    @Value("${custom.dev.to.api.key}")
    private String apiKey;
    private final String BASE_URL = "https://dev.to/api/articles";
    ObjectMapper objectMapper = new ObjectMapper();

    public List<Article> getTop20ArticlesByUsername(String username) {
        String processUsername = URLEncoder.encode(username.trim(), StandardCharsets.UTF_8);
        System.out.println(processUsername);
        String url = BASE_URL + "&username=" + processUsername;
        System.out.println(url);
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
                    return top20Articles;
                } else {
                    System.out.println("Failed to fetch articles. Status code: " + response.statusCode());
                }
            } catch (UncheckedIOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return List.of();
    }

    public List<String> getTop20ArticleIdsByUsername(String username) {
        String processUsername = URLEncoder.encode(username.trim(), StandardCharsets.UTF_8);
        System.out.println(processUsername);
        String url = BASE_URL + "?username=" + processUsername;
        System.out.println(url);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("api-key", apiKey) // Add the API key in the request header
                    .GET()
                    .build();

            // Send Request and Receive Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();


                JsonNode articlesNode = objectMapper.readTree(jsonResponse);

                List<String> articleIds = new ArrayList<>();
                if (articlesNode.isArray()) {
                    int count = 0;
                    for (JsonNode articleNode : articlesNode) {
                        if (count >= 20) break;
                        articleIds.add(articleNode.get("id").asText());
                        count++;
                    }
                }
                return articleIds;
            } else {
                System.out.println("Failed to fetch articles. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return List.of();
    }

    public Map<String, Object> fetchArticleInfo(String articleId) {
        String url = BASE_URL + "/" + articleId;
        try (HttpClient client = HttpClient.newHttpClient()) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                ObjectMapper objectMapper = new ObjectMapper();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {

                    return objectMapper.readValue(response.body(), Map.class);
                } else {
                    throw new RuntimeException("Failed to fetch article, Status Code: " + response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error fetching article: " + e.getMessage());
            }
        }
    }
}
