package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.services.ProcessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/process-user")
public class ProcessUserController {
    private final ProcessUserService processUserService;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> processUser(@PathVariable String userId) {
        processUserService.processUser(userId);
        return ResponseEntity.ok().build();
    }
}
