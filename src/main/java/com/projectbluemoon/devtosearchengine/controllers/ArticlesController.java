package com.projectbluemoon.devtosearchengine.controllers;

import com.projectbluemoon.devtosearchengine.services.ArticleProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ArticlesController {
    private final ArticleProcessingService articleProcessingService;

    @GetMapping("/articles")
    public ResponseEntity<Object> fetchArticles(@RequestBody Map<String, Object> body) {
        List<Map<String, Object>> result = articleProcessingService.fetchUserArticles(body.get("username").toString());
        return ResponseEntity.ok(result);
    }
}
