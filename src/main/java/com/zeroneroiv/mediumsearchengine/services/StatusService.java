package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final DBArticleRepository DBArticleRepository;

    public List<String> getUserArticlesStatus(Long userId) {
        List<Article> result = DBArticleRepository.findAllByAuthorId(userId);

        List<String> output = new ArrayList<>();

        output.add("!--CURRENT REQUEST STATUS STARTED--!");
        output.add("!-- FOR AUTHOR #" + userId.toString());
        for (Article article : result) output.add(formOutputForArticle(article));
        output.add("!--CURRENT REQUEST STATUS FINISHED--!");

        return output;
    }

    private String formOutputForArticle(Article article) {
        return "---\n" +
                "Article #" + article.getId().toString() + "\n" +
                "Status: " + article.getStatus().toString().toUpperCase() + "\n" +
                "---";
    }
}
