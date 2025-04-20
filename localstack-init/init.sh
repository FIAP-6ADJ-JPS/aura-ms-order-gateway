#!/bin/bash

# Criar fila SQS
echo "Creating SQS Queue: new-order-queue"
awslocal sqs create-queue --queue-name new-order-queue

echo "LocalStack initialization completed!"

