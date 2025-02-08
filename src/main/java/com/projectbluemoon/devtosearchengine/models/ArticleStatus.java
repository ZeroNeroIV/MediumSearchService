package com.projectbluemoon.devtosearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class ArticleStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String articleId;
    private String elasticSearchDocumentId;
    private ProcessingStatus status = ProcessingStatus.PENDING;

    public enum ProcessingStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }
}