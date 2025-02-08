package com.zeroneroiv.mediumsearchengine.services;

import com.zeroneroiv.mediumsearchengine.models.ArticleStatus;
import com.zeroneroiv.mediumsearchengine.repositories.DBArticleRepository;
import com.zeroneroiv.mediumsearchengine.repositories.ESArticleRepository;
import com.zeroneroiv.mediumsearchengine.utilities.MediumApiUtility;
import com.zeroneroiv.mediumsearchengine.utilities.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class ProcessUserService {
    private final MediumApiUtility mediumApiUtility;
    private final DBArticleRepository dbArticleRepository;
    private final ESArticleRepository esArticleRepository;

    // just for trying
    private static final int QUEUE_CAPACITY = 5; // Max articles in queue
    private static final ArrayBlockingQueue<Worker> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    // Create a thread pool with a fixed number of threads
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void processUser(String userId) {
        List<String> results = mediumApiUtility.fetchUserTopArticles(userId);

        for (String result : results) {
            ArticleStatus articleStatus = new ArticleStatus();
            articleStatus.setArticleId(result);
            articleStatus.setAuthorId(userId);
            dbArticleRepository.save(articleStatus);
        }

        CompletableFuture.runAsync(() -> processArticleAsync(results));
    }

    private void processArticleAsync(List<String> articleIds) {
        articleIds.forEach(
                (String articleId) -> {
                    Worker worker = new Worker();
                    while (!queue.offer(worker)) ;
                    worker.doWorker(
                            articleId,
                            queue,
                            dbArticleRepository,
                            esArticleRepository,
                            mediumApiUtility);
                });
    }
}
