package com.example.bff.domain;

import com.example.zoostore.restexport.ZooStoreRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;

public class RestClientFactory {
    @Bean
    ZooStoreRestClient getRestExportClientStore() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(ZooStoreRestClient.class, "http://localhost:8081");
    }

//    @Bean
//    StorageRestClient getRestExportClientStorage() {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        return Feign.builder()
//                .encoder(new JacksonEncoder(objectMapper))
//                .decoder(new JacksonDecoder(objectMapper))
//                .target(ZooStoreRestClient.class, "http://localhost:8082");
//    }
}
