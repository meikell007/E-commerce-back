package com.unimag.ecommercexyz.infrastructure.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

public class DynamoRepository {

    private final DynamoDbEnhancedClient client;

    public DynamoRepository(DynamoDbEnhancedClient client) {
        this.client = client;
    }


}
