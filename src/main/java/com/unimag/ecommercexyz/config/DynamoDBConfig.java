package com.unimag.ecommercexyz.config;

import com.unimag.ecommercexyz.entity.OrderItemEntity;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

@Configuration
public class DynamoDBConfig {
    @Value("${aws.dynamodb.endpoint:}")
    private String endpoint;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.accessKey:}")
    private String accessKey;

    @Value("${aws.secretKey:}")
    private String secretKey;

    @Bean
    public DynamoDbClient dynamoDbClient() {

        DynamoDbClientBuilder builder = DynamoDbClient.builder()
                .region(Region.of(region));

        //Si hay endpoint → estás en local
        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(endpoint));
        }

        //Credenciales (solo si están definidas)
        if (accessKey != null && !accessKey.isEmpty()) {
            AwsBasicCredentials credentials =
                    AwsBasicCredentials.create(accessKey, secretKey);

            builder.credentialsProvider(
                    StaticCredentialsProvider.create(credentials)
            );
        }

        return builder.build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient client) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();
    }

    @Bean
    public DynamoDbTable<UserEntity> userAccessTable(DynamoDbEnhancedClient client) {
        return client.table("AppTable", TableSchema.fromBean(UserEntity.class));
    }

    @Bean
    public DynamoDbTable<OrderEntity> orderAccessTable(DynamoDbEnhancedClient client) {
        return client.table("AppTable", TableSchema.fromBean(OrderEntity.class));
    }

    @Bean
    public DynamoDbTable<OrderItemEntity> orderEntityAccessTable(DynamoDbEnhancedClient client) {
        return client.table("AppTable", TableSchema.fromBean(OrderItemEntity.class));
    }
}
