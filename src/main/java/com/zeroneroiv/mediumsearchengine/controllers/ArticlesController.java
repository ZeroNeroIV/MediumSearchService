package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.services.ArticleProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ArticlesController {
    private final ArticleProcessingService articleProcessingService;

    @GetMapping("/articles/{userId}")
    public ResponseEntity<Object> fetchArticles(@PathVariable String userId) {
        List<Map<String, Object>> result = articleProcessingService.fetchUserArticles(userId);
        return ResponseEntity.ok(result);
    }
}
