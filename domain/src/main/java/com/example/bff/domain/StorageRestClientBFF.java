package com.example.bff.domain;

import com.example.storage.restexport.StorageRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageRestClientBFF {
    @Bean
    public StorageRestClient getStorageRestClient(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(com.example.storage.restexport.StorageRestClient.class, "http://localhost:8082");
    }
}
