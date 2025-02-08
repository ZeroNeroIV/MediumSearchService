package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.ArticleStatus;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final DBArticleRepository DBArticleRepository;

    public List<ArticleStatus> getUserArticlesStatus(String userId) {
        return DBArticleRepository.findAllByAuthorId(userId);
    }
//    public List<String> getUserArticlesStatus(String userId) {
//        List<Article> result = DBArticleRepository.findAllByAuthorId(userId);
//
//        List<String> output = new ArrayList<>();
//
//        output.add("!--CURRENT REQUEST STATUS STARTED--!");
//        output.add("!-- FOR AUTHOR #" + userId);
//        for (Article article : result) output.add(formOutputForArticle(article));
//        output.add("!--CURRENT REQUEST STATUS FINISHED--!");
//
//        return output;
//    }
//
//    private String formOutputForArticle(Article article) {
//        return "---\n" +
//                "Article #" + article.getArticleId() + "\n" +
//                "Status: " + article.getStatus().toString().toUpperCase() + "\n" +
//                "---";
//    }
}
