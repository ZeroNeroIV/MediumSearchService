package com.zeroneroiv.mediumsearchengine.controllers;

import com.zeroneroiv.mediumsearchengine.services.ProcessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProcessUserController {
    private final ProcessUserService processUserService;

    @PostMapping("/process-user/{userId}")
    public ResponseEntity<Object> processUser(@PathVariable String userId) {
        processUserService.processUser(userId);
        return ResponseEntity.ok().build();
    }
}
