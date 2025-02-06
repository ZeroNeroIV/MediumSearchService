package com.zeroneroiv.mediumsearchengine.configurations;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfiguration {
    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(
                        "localhost",
                        9200,
                        "http")
        ));
    }
//@Bean
//public ElasticsearchClient elasticsearchClient() {
//    RestClient restClient = RestClient.builder(
//            new HttpHost("localhost", 9200, "http")
//    ).build();
//
//    RestClientTransport transport = new RestClientTransport(
//            restClient, new JacksonJsonpMapper()
//    );
//
//    return new ElasticsearchClient(transport);
//}
}
