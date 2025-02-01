package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.repositories.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessUserService {
    private final UserAccountRepository userAccountRepository;

    public void processUser(String userId) {
        // Todo
    }
}
