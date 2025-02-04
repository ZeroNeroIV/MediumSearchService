package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleProcessingService {
    private final ArticleRepository articleRepository;

    public List<Article> fetchUserArticles(Long userId) {
        return articleRepository.findAllReadyByAuthorId(userId);
    }
}
