package com.postech.auramsordergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuraMsOrderGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuraMsOrderGatewayApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}