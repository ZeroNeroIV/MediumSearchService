package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/status")
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> status(@PathVariable Long userId) {
        List<String> result = statusService.getUserArticlesStatus(userId);
        return ResponseEntity.ok(result);
    }
}
