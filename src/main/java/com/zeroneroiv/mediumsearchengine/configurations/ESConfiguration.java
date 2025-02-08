package com.zeroneroiv.mediumsearchengine.configurations;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ESConfiguration {

    @Value("${custom.elasticsearch.host}")
    private String host;
    @Value("${custom.elasticsearch.port}")
    private int port;

    @Bean
    public ElasticsearchClient client() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // Create SSL context
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()) // Trust self-signed certificates
                .build();
//        // Configure the client builder
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//                        .setSSLContext(sslContext)
//                        // Bypass hostname verification (not recommended for production)
//                        .setSSLHostnameVerifier((hostname, session) -> true)
//                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
//                        .setConnectTimeout(5000) // 5 seconds
//                        .setSocketTimeout(60000) // 60 seconds
//                        .setAuthenticationEnabled(false)

        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setSSLContext(sslContext)
                        // Bypass hostname verification (not recommended for production)
                        .setSSLHostnameVerifier((hostname, session) -> true)
                )
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(5000) // 5 seconds
                        .setSocketTimeout(60000) // 60 seconds
                        .setAuthenticationEnabled(false)
                )
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }

}
