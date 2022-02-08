package com.ftn.mas.elasticSearch.Backend.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.baeldung.spring.data.es.repository")
@ComponentScan(basePackages = { "com.baeldung.spring.data.es.service" })
public class Config {
    
    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration 
            = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
    
    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
          new HttpClientConfig.Builder("http://localhost:9200")
            .multiThreaded(true)
            .defaultMaxTotalConnectionPerRoute(2)
            .maxTotalConnection(10)
            .build());
        return factory.getObject();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
