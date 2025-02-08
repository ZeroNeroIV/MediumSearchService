package com.projectbluemoon.devtosearchengine.services;

import com.projectbluemoon.devtosearchengine.models.ArticleStatus;
import com.projectbluemoon.devtosearchengine.repositories.DBArticleRepository;
import com.projectbluemoon.devtosearchengine.repositories.ESArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleProcessingService {
    private final DBArticleRepository dbArticleRepository;
    private final ESArticleRepository esArticleRepository;

    public List<Map<String, Object>> fetchUserArticles(String username) {
        List<ArticleStatus> articleStatuses = dbArticleRepository.findAllByUsername(username);
        List<Map<String, Object>> result = new ArrayList<>();
        articleStatuses.forEach(article -> {
            try {
                result.add(esArticleRepository.getArticleById(article.getElasticSearchDocumentId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}
