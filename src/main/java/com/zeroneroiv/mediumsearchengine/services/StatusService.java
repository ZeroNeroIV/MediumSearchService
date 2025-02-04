package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final ArticleRepository articleRepository;


    public List<String> getUserArticlesStatus(Long userId) {
        List<Article> result = articleRepository.findAllByAuthorId(userId);

        List<String> output = new java.util.ArrayList<>();

        for (Article article : result) output.add(formOutputForArticle(article));

        return output;
    }

    private String formOutputForArticle(Article article) {
        return "\n---\n" +
                "Article #" + article.getId().toString() + "\n" +
                "Author #" + article.getAuthorId().toString() + "\n" +
                "Status: " + article.getStatus().toString().toUpperCase() + "\n" +
                "---\n";
    }
}
