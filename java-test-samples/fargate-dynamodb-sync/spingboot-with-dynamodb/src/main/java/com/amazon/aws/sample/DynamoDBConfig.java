package com.amazon.aws.sample;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazon.aws.sample.model.Customer;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

@Configuration
public class DynamoDBConfig {
    private static final Logger logger = LoggerFactory.getLogger(DynamoDBConfig.class);

    @Value("${cloud.aws.region.static}")
    public String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<Customer> customerTable(DynamoDbEnhancedClient enhancedClient, DynamoDbClient dynamoDbClient) {
        DynamoDbTable<Customer> table = enhancedClient.table("Customer", TableSchema.fromBean(Customer.class));
        init(table, dynamoDbClient);
        return table;
    }

    // Initializes the DynamoDB table for the Customer class
    public void init(DynamoDbTable<Customer> table, DynamoDbClient dynamoDbClient) {
        try {
            dynamoDbClient.describeTable(DescribeTableRequest.builder().tableName("Customer").build());
        } catch (ResourceNotFoundException e) {
            table.createTable();
            logger.info("Table created successfully");
        }
    }
}
