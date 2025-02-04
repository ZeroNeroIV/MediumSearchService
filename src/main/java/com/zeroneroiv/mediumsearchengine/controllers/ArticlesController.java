package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.services.ArticleProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/articles")
public class ArticlesController {
    private final ArticleProcessingService articleProcessingService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> fetchArticles(@PathVariable Long userId) {
        List<Article> result = articleProcessingService.fetchUserArticles(userId);
        return ResponseEntity.ok(result);
    }
}
