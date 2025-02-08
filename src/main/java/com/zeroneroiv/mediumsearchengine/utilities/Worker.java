package com.zeroneroiv.mediumsearchengine.utilities;

import com.zeroneroiv.mediumsearchengine.models.ArticleStatus;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import com.zeroneroiv.mediumsearchengine.repositories.ESArticleRepository;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Worker implements Runnable {
    private DBArticleRepository dbArticleRepository;
    private ESArticleRepository esArticleRepository;
    MediumApiUtility mediumApiUtility;
    String articleId;

    public void doWorker(
            String id,
            ArrayBlockingQueue<Worker> queue,
            DBArticleRepository dbRepo,
            ESArticleRepository esRepo,
            MediumApiUtility mediumApiUtil

    ) {
        articleId = id;
        mediumApiUtility = mediumApiUtil;
        dbArticleRepository = dbRepo;
        esArticleRepository = esRepo;
        run(); // Logic
        queue.poll();
    }

    @Override
    public void run() {
        // Fetch from medium
        Map<String, Object> articleInfo = mediumApiUtility.fetchArticleInfo(articleId);
        ArticleStatus articleStatus = dbArticleRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // Send to ES
        String esId = esArticleRepository.saveArticle(articleInfo);
        articleStatus.setElasticSearchDocumentId(esId);

        // update db-queue
        articleStatus.setStatus(ArticleStatus.ProcessingStatus.COMPLETED);
        dbArticleRepository.save(articleStatus);
    }
}
