package com.projectbluemoon.devtosearchengine.services;

import com.projectbluemoon.devtosearchengine.models.ArticleStatus;
import com.projectbluemoon.devtosearchengine.repositories.DBArticleRepository;
import com.projectbluemoon.devtosearchengine.repositories.ESArticleRepository;
import com.projectbluemoon.devtosearchengine.utilities.DevToApiUtility;
import com.projectbluemoon.devtosearchengine.utilities.Worker;
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
    //    private final MediumApiUtility mediumApiUtility;
    private final DBArticleRepository dbArticleRepository;
    private final ESArticleRepository esArticleRepository;
    private final DevToApiUtility devToApiUtility;
    // just for trying
    private static final int QUEUE_CAPACITY = 5; // Max articles in queue
    private static final ArrayBlockingQueue<Worker> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    // Create a thread pool with a fixed number of threads
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void processUser(String username) {
        List<String> results = devToApiUtility.getTop20ArticleIdsByUsername(username);

        for (String result : results) {
            ArticleStatus articleStatus = new ArticleStatus();
            articleStatus.setArticleId(result);
            articleStatus.setUsername(username);
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
                            devToApiUtility);
                });
    }
}
