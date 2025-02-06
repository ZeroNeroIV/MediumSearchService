package com.zeroneroiv.mediumsearchengine.repositories;

import com.zeroneroiv.mediumsearchengine.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DBArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT x FROM Article x WHERE x.status == x.ProcessingStatus.COMPLETED and x.authorId == ?1")
    List<Article> findAllReadyByAuthorId(Long authorId);

    @Query("SELECT x FROM Article x WHERE x.authorId == ?1")
    List<Article> findAllByAuthorId(Long authorId);
}
