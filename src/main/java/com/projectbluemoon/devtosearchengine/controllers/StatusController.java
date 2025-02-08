package com.projectbluemoon.devtosearchengine.controllers;

import com.projectbluemoon.devtosearchengine.models.ArticleStatus;
import com.projectbluemoon.devtosearchengine.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/status")
    public ResponseEntity<List<ArticleStatus>> status(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(statusService.getUserArticlesStatus(body.get("username").toString()));
    }
}
