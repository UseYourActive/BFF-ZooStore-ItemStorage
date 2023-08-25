package com.example.bff.domain;

import com.example.storage.restexport.ItemStorageRestExport;
import com.example.storage.restexport.StorageRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StorageRestClientBFF {
    private final ApplicationContext context;

    @Value("${ITEM_STORAGE_URL}")
    private String ITEM_STORAGE_URL;

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.findAndRegisterModules();
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public ItemStorageRestExport getStorageRestClient(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(context.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(context.getBean(ObjectMapper.class)))
                .target(ItemStorageRestExport.class, ITEM_STORAGE_URL);
    }
}
