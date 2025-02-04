package com.zeroneroiv.mediumsearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Table
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String url;
    private Long authorId;
    private ProcessingStatus status = ProcessingStatus.PENDING;

    public enum ProcessingStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }
}
