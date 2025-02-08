package com.projectbluemoon.devtosearchengine.models;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "devto_article")
public class Article {
    private String title;

    private String url;

    private int positiveReactionsCount;

    private String publishedAt;
}
