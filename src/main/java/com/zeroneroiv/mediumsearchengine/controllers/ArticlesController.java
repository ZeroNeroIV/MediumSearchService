package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.services.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/articles")
public class ArticlesController {
    private final ArticlesService articlesService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> fetchArticles(@PathVariable String userId) {
        List<Article> result = articlesService.fetchProcessedUserArticles(userId);
        return ResponseEntity.ok(result);
    }
}
