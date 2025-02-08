package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.ArticleStatus;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import com.zeroneroiv.mediumsearchengine.repositories.ESArticleRepository;
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

    public List<Map<String, Object>> fetchUserArticles(String userId) {
        List<ArticleStatus> articleStatuses = dbArticleRepository.findAllByAuthorId(userId);
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
