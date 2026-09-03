package com.amazon.aws.sample.utils;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.stereotype.Service;

@Service
public interface AmazonDynamoDBClientBuilderService {
    DynamoDbClient buildClient();
}
