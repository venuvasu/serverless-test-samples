package com.amazon.aws.sample.utils;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamoDBConfig {

    @Autowired
    AmazonDynamoDBClientBuilderService amazonDynamoDBClientBuilderService;

    /**
     * This method is used to getOrderStatus from dynamoDb
     * @param orderId
     * @return
     */
    public String getOrderStatus(String orderId) {
        DynamoDbClient client = amazonDynamoDBClientBuilderService.buildClient();

        /* Create a Map of Primary Key attributes */
        Map<String, AttributeValue> keysMap = new HashMap<>();
        keysMap.put("orderId", AttributeValue.builder().s(orderId).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName("order_details")
                .projectionExpression("orderStatus")
                .key(keysMap)
                .build();

        /* Send Get Item Request */
        GetItemResponse result = client.getItem(request);
        return String.valueOf(result.item().get("orderStatus").s());
    }

}
