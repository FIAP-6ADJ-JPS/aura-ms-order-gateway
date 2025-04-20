package com.postech.auramsordergateway.config.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Value("${cloud.aws.endpoint}")
    private String awsEndpoint;

    @Value("${cloud.aws.region}")
    private String awsRegion;

    @Bean
    public SqsClient sqsClient() {
        // Para o LocalStack, usamos credenciais fictícias
        AwsBasicCredentials credentials = AwsBasicCredentials.create("test", "test");

        return SqsClient.builder()
                .region(Region.of(awsRegion))
                .endpointOverride(URI.create(awsEndpoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}