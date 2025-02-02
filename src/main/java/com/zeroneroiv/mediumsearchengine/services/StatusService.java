package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final ArticleRepository articleRepository;


    public List<String> getUserArticlesStatus(String userId) {
        // Todo
        return List.of();
    }
}
