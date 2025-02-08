package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.models.ArticleStatus;
import com.zeroneroiv.mediumsearchengine.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/status/{userId}")
    public ResponseEntity<List<ArticleStatus>> status(@PathVariable String userId) {
        return ResponseEntity.ok(statusService.getUserArticlesStatus(userId));
    }
}
