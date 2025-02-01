package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final ArticleRepository articleRepository;


    public String getUserArticlesStatus(String userId) {
        // Todo
        return "";
    }
}
