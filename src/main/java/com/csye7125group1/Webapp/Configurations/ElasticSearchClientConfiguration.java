package com.csye7125group1.Webapp.Configurations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages
        = "com.csye7125group1.Webapp.Repositories")
@ComponentScan(basePackages = { "com.csye7125group1.Webapp" })
public class ElasticSearchClientConfiguration{

    @Value(value = "${spring.elasticsearch.server.hostname}")
    private String hostname;
    @Value(value = "${spring.elasticsearch.server.portno}")
    private int portno;

    @Bean
    public ElasticsearchClient getElasticSearchClient(){

        RestClient httpClient = RestClient.builder(
                new HttpHost(hostname, portno)
        ).build();

        ElasticsearchTransport transport = new RestClientTransport(
                httpClient,
                new JacksonJsonpMapper()
        );

        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        return esClient;
    }
}


