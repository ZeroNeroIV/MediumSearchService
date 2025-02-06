package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.Article;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import com.zeroneroiv.mediumsearchengine.utilities.MediumApiUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessUserService {
    private final MediumApiUtility mediumApiUtility;
    private final DBArticleRepository DBArticleRepository;

    public void processUser(Long userId) {
        List<Article> results = mediumApiUtility.fetchTopArticles(userId, 20);
        DBArticleRepository.saveAll(results);
        // TODO
        // Pipeline
    }
    public void storeInES(List<Article> articleList) {
        // TODO
    }

}
