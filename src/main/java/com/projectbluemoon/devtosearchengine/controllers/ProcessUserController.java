package com.projectbluemoon.devtosearchengine.controllers;

import com.projectbluemoon.devtosearchengine.services.ProcessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ProcessUserController {
    private final ProcessUserService processUserService;

    @PostMapping("/process-user")
    public ResponseEntity<Object> processUser(@RequestBody Map<String, Object> body) {
        processUserService.processUser(body.get("username").toString());
        return ResponseEntity.ok().build();
    }
}
