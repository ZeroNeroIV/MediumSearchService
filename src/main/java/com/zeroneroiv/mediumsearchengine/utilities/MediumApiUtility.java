package com.zeroneroiv.mediumsearchengine.utilities;

import com.zeroneroiv.mediumsearchengine.models.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediumApiUtility {

    public List<Article> fetchTopArticles(Long userId, int count) {
        // Mock implementation.
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Article article = new Article();
            article.setAuthorId(userId);
            article.setTitle("Sample Article " + (i + 1));
            article.setContent("This is the content of sample article " + (i + 1));
            article.setUrl("https://medium.com/@" + userId + "/article-" + (i + 1));
            articles.add(article);
        }
        return articles;
    }
}

