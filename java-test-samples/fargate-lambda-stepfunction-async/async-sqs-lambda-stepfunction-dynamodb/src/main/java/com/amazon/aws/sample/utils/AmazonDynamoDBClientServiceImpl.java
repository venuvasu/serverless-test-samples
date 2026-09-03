package com.amazon.aws.sample.utils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmazonDynamoDBClientServiceImpl implements AmazonDynamoDBClientBuilderService {

    @Value("${cloud.aws.region.static}")
    private String region;
    @Override
    public DynamoDbClient buildClient() {
        return DynamoDbClient.builder()
                .region(Region.of(region)).build();
    }
}
