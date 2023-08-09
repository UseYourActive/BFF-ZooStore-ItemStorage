package com.example.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class) kogato nqmame baza danni - JPA repository
@ComponentScan(basePackages = {"com.example.bff"})
@EntityScan(basePackages = {"com.example.bff.persistence.entities"})
@EnableJpaRepositories(basePackages = {"com.example.bff.persistence.repositories"})
@EnableFeignClients(basePackages = "com.example.bff")
@EnableScheduling
public class BffApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }
}
