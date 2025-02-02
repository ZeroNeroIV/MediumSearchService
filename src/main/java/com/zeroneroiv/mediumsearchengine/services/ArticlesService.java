package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticlesService {
    private final ArticleRepository articleRepository;

    public List<Article> fetchProcessedUserArticles(String userId) {
        List<Article> result = List.of();
        // Todo
        return result;
    }
}
