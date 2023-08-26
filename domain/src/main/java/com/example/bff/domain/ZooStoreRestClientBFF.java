package com.example.bff.domain;

import com.example.zoostore.restexport.ZooStoreRestExport;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ZooStoreRestClientBFF {
    private final ApplicationContext context;

    @Value("${ZOO_STORE_URL}")
    private String ZOO_STORE_URL;

    @Bean
    public ZooStoreRestExport getZooStoreRestClient(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(context.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(context.getBean(ObjectMapper.class)))
                .target(ZooStoreRestExport.class, ZOO_STORE_URL);
    }
}
