package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.ArticleRepository;
import com.zeroneroiv.mediumsearchengine.utilities.MediumApiUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessUserService {
    private final MediumApiUtility mediumApiUtility;
    private final ArticleRepository articleRepository;

    public void processUser(Long userId) {
        List<Article> results = mediumApiUtility.fetchTopArticles(userId, 20);
        articleRepository.saveAll(results);
        // TODO
    }
}
