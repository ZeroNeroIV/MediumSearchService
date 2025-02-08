package com.projectbluemoon.devtosearchengine.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ESArticleRepository {
    private final ElasticsearchClient client;
    private final ObjectMapper objectMapper;
    @Value("${custom.elasticsearch.index-name}")
    private String indexName;

    // Save an article to Elasticsearch.
    public String saveArticle(Map<String, Object> article) {
        try {
            // Convert the article map to a JSON string
            String jsonString = objectMapper.writeValueAsString(article);

            // Execute the request and get the response
            IndexResponse response = client.index(i -> i
                    .index(indexName)
                    .withJson(new StringReader(jsonString)
                    ));

            return response.id();
        } catch (IOException e) {
            System.err.println("Failed to save article: " + e.getMessage());
            throw new RuntimeException("Failed to save article", e);
        }
    }

    // Fetch articles by author ID.
    public List<Map<String, Object>> getArticlesByAuthorId(String authorId) throws IOException {
        MatchQuery matchQuery = MatchQuery.of(a -> a
                .field("authorId")
                .query(authorId)
        );

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(indexName)
                .query(q -> q.match(matchQuery))
        );

        SearchResponse<Map> searchResponse = client.search(searchRequest, Map.class);

        List<Map<String, Object>> articles = new ArrayList<>();
        for (Hit<Map> hit : searchResponse.hits().hits()) {
            Map<String, Object> article = hit.source();
            articles.add(article);
        }
        return articles;
    }

    // Retrieve full article info using elasticSearchDocumentId stored inside each article instance
    public Map<String, Object> getArticleById(String elasticSearchDocumentId) throws IOException {
        GetRequest getRequest = GetRequest.of(g -> g
                .index(indexName)
                .id(elasticSearchDocumentId)
        );

        GetResponse<Map> getResponse = client.get(getRequest, Map.class);

        if (getResponse.found()) {
            return getResponse.source();
        } else {
            return null;
        }
    }

    // EXTRA: Make a searching method that takes a query-like structure as parameter for searching
}
