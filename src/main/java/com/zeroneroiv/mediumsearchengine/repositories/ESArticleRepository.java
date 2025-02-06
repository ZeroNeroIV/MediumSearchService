package com.zeroneroiv.mediumsearchengine.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroneroiv.mediumsearchengine.models.Article;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ESArticleRepository {
    @Value("custom.elasticsearch.index-name")
    private String indexName;
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public String saveArticle(Map<String, Object> article) throws IOException {
        String jsonString = objectMapper.writeValueAsString(article);

        IndexRequest request = new IndexRequest(indexName)
                .source(jsonString, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        return response.getId();
    }

    public List<Map<String, Object>> getArticlesByAuthorId(String authorId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("authorId", authorId);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> articles = new ArrayList<>();
        for(SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> article = hit.getSourceAsMap();
            articles.add(article);
        }
        return articles;
    }

    // EXTRA: Make a searching method that takes a query-like structure as parameter for searching
}
