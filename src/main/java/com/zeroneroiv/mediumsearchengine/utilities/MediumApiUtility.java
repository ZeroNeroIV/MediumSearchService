package com.zeroneroiv.mediumsearchengine.utilities;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//! Mock implementation.
@Service
public class MediumApiUtility {
    Map<String, Map<String, Object>> mediumArticles = new HashMap<>();

    public List<String> fetchUserTopArticles(String userId) {
        List<String> articleIds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articleIds.add(String.valueOf(i));
            makeArticle(String.valueOf(i), userId);
        }
        return articleIds;
    }

    public Map<String, Object> fetchArticleInfo(String userId) {
        for (int i = 0; i < 20; i++)
            if (mediumArticles.containsKey(i + userId))
                return mediumArticles.get(i + userId);
        return Map.of("error", "No medium article found");
    }

    private void makeArticle(String articleId, String authorId) {
        Map<String, Object> mediumArticle = new HashMap<>();

        mediumArticle.put("id", articleId);
        mediumArticle.put("title", "some title: " + articleId);
        mediumArticle.put("author", "some author: " + authorId);
        mediumArticle.put("url", "http://notmediummocker.com?author_id=" + authorId + "&article_id=" + articleId);

        mediumArticles.put(articleId + authorId, mediumArticle);
    }

}

