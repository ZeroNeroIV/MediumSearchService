package com.projectbluemoon.devtosearchengine.utilities;

import com.projectbluemoon.devtosearchengine.models.ArticleStatus;
import com.projectbluemoon.devtosearchengine.repositories.DBArticleRepository;
import com.projectbluemoon.devtosearchengine.repositories.ESArticleRepository;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Worker implements Runnable {
    DevToApiUtility devToApiUtility;
    String articleId;
    private DBArticleRepository dbArticleRepository;
    private ESArticleRepository esArticleRepository;

    public void doWorker(
            String id,
            ArrayBlockingQueue<Worker> queue,
            DBArticleRepository dbRepo,
            ESArticleRepository esRepo,
            DevToApiUtility devToApiUtility

    ) {
        articleId = id;
        dbArticleRepository = dbRepo;
        esArticleRepository = esRepo;
        run(); // Logic
        queue.poll();
    }

    @Override
    public void run() {
        ArticleStatus articleStatus = dbArticleRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        articleStatus.setStatus(ArticleStatus.ProcessingStatus.PROCESSING);
        try {
            // Fetch from devto
            Map<String, Object> articleInfo = devToApiUtility.fetchArticleInfo(articleId);

            // Send to ES
            String esId = esArticleRepository.saveArticle(articleInfo);
            articleStatus.setElasticSearchDocumentId(esId);

            // update db-queue
            articleStatus.setStatus(ArticleStatus.ProcessingStatus.COMPLETED);
            dbArticleRepository.save(articleStatus);
        } catch (Exception e) {
            articleStatus.setStatus(ArticleStatus.ProcessingStatus.FAILED);
        }
    }
}
