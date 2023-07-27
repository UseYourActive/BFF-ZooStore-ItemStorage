package com.example.bff.domain;

import com.example.bff.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RestClientFactory {
    private final ObjectMapper objectMapper;

    @Bean
    ZooStoreRestClient getRestExportClientZooStore() {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(ZooStoreRestClient.class, "http://localhost:8081");
    }

    @Bean
    StorageRestClient getRestExportClientStorage() {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(StorageRestClient.class, "http://localhost:8082");
    }
}
