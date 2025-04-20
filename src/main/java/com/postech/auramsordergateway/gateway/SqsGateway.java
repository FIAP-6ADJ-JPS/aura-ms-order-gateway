package com.postech.auramsordergateway.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.auramsordergateway.domain.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsGateway {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.sqs.queue-name}")
    private String queueName;

    public String sendOrderToQueue(OrderRequest orderRequest) {
        try {
            String orderJson = objectMapper.writeValueAsString(orderRequest);

            String queueUrl = getQueueUrl();

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(orderJson)
                    .build();

            SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
            log.info("Message sent to SQS. Message ID: {}", response.messageId());

            return response.messageId();
        } catch (JsonProcessingException e) {
            log.error("Failed to convert order to JSON", e);
            throw new RuntimeException("Error sending message to queue", e);
        }
    }

    private String getQueueUrl() {
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();
        return sqsClient.getQueueUrl(getQueueRequest).queueUrl();
    }
}