package com.projectbluemoon.devtosearchengine.repositories;

import com.projectbluemoon.devtosearchengine.models.ArticleStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DBArticleRepository extends JpaRepository<ArticleStatus, String> {

    @Query("SELECT a FROM ArticleStatus a WHERE a.articleId = ?1")
    Optional<ArticleStatus> findByArticleId(String articleId);

    @Query("SELECT x FROM ArticleStatus x WHERE x.status = ?2 and x.username = ?1")
    List<ArticleStatus> findAllReadyByUsername(String username, ArticleStatus.ProcessingStatus status);

    @Query("SELECT x FROM ArticleStatus x WHERE x.username = ?1")
    List<ArticleStatus> findAllByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM ArticleStatus x WHERE x.articleId = ?1")
    void deleteByArticleId(String articleId);
}
