package com.example.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.bff"})
//@EntityScan(basePackages = {"com.example.bff.persistence.entities"})
//@EnableJpaRepositories(basePackages = {"com.example.bff.persistence.repositories"})
public class BffApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }
}
