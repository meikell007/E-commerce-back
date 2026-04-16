package com.unimag.ecommercexyz.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamoDBInitializer {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBInitializer(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeTables() {
        createEcommerceTable();
        insertSampleData();
    }

    private void createEcommerceTable() {
        try {
            ListTablesResponse response = dynamoDbClient.listTables();

            if (response.tableNames().contains("E-commerce")) {
                System.out.println("✓ Tabla 'E-commerce' ya existe");
                return;
            }

            System.out.println("📋 Creando tabla 'E-commerce'...");

            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName("E-commerce")
                    .keySchema(
                            KeySchemaElement.builder()
                                    .attributeName("pk")
                                    .keyType(KeyType.HASH)
                                    .build(),
                            KeySchemaElement.builder()
                                    .attributeName("sk")
                                    .keyType(KeyType.RANGE)
                                    .build()
                    )
                    .attributeDefinitions(
                            AttributeDefinition.builder()
                                    .attributeName("pk")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("sk")
                                    .attributeType(ScalarAttributeType.S)
                                    .build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            dynamoDbClient.createTable(request);
            System.out.println("✓ Tabla 'E-commerce' creada exitosamente");

            Thread.sleep(2000);
        } catch (ResourceInUseException e) {
            System.out.println("✓ Tabla 'E-commerce' ya existe");
        } catch (Exception e) {
            System.err.println("❌ Error creando tabla: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertSampleData() {
        try {
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName("E-commerce")
                    .build();

            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

            if (scanResponse.count() > 0) {
                System.out.println("✓ Datos ya existen en la tabla (" + scanResponse.count() + " items)");
                return;
            }

            System.out.println("📝 Insertando datos de ejemplo...");

            // 1. USER#001 PROFILE
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("PROFILE").build(),
                    "type", AttributeValue.builder().s("USER_PROFILE").build(),
                    "name", AttributeValue.builder().s("Luisa").build(),
                    "email", AttributeValue.builder().s("l@x.com").build(),
                    "shippingAddress", AttributeValue.builder().s("Calle 10, Bogotá").build(),
                    "paymentMethod", AttributeValue.builder().s("Visa ...1234").build()
            ));

            // 2. USER#001 ORDER#556
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("ORDER#556").build(),
                    "type", AttributeValue.builder().s("ORDER").build(),
                    "status", AttributeValue.builder().s("Pago exitoso").build(),
                    "creationDate", AttributeValue.builder().s("2023-11-15T14:30Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Calle 10, Bogotá").build()

            ));

            // 3. USER#001 ORDER#554
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("ORDER#554").build(),
                    "type", AttributeValue.builder().s("ORDER").build(),
                    "status", AttributeValue.builder().s("Enviado").build(),
                    "creationDate", AttributeValue.builder().s("2023-11-01T09:15Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Calle 10, Bogotá").build()
            ));

            // 4. USER#001 ORDER#555
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("ORDER#555").build(),
                    "type", AttributeValue.builder().s("ORDER").build(),
                    "status", AttributeValue.builder().s("Pago exitoso").build(),
                    "creationDate", AttributeValue.builder().s("2023-10-27T08:00Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Calle 10, Bogotá").build()
            ));

            // 5. USER#001 ORDER#553
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("ORDER#553").build(),
                    "type", AttributeValue.builder().s("ORDER").build(),
                    "status", AttributeValue.builder().s("Enviado").build(),
                    "creationDate", AttributeValue.builder().s("2023-10-10T11:45Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Ave. 5, Medellín").build()
            ));

            // 6. USER#001 ORDER#552
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("USER#001").build(),
                    "sk", AttributeValue.builder().s("ORDER#552").build(),
                    "type", AttributeValue.builder().s("ORDER").build(),
                    "status", AttributeValue.builder().s("Pago exitoso").build(),
                    "creationDate", AttributeValue.builder().s("2023-09-25T16:00Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Ave. 5, Medellín").build()
            ));

            // 7. ORDER#555 DETAILS
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("ORDER#555").build(),
                    "sk", AttributeValue.builder().s("DETAILS").build(),
                    "type", AttributeValue.builder().s("ORDER_DETAILS").build(),
                    "status", AttributeValue.builder().s("Pago exitoso").build(),
                    "creationDate", AttributeValue.builder().s("2023-10-27T08:00Z").build(),
                    "shippingAddress", AttributeValue.builder().s("Calle 10, Bogotá").build()
            ));

            // 8. ORDER#555 ITEM#001
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("ORDER#555").build(),
                    "sk", AttributeValue.builder().s("ITEM#001").build(),
                    "type", AttributeValue.builder().s("ITEM").build(),
                    "name", AttributeValue.builder().s("Laptop XPS").build(),
                    "quantityRequested", AttributeValue.builder().n("1").build(),
                    "price", AttributeValue.builder().n("1200").build()
            ));

            // 9. ORDER#555 ITEM#002
            insertItem(Map.of(
                    "pk", AttributeValue.builder().s("ORDER#555").build(),
                    "sk", AttributeValue.builder().s("ITEM#002").build(),
                    "type", AttributeValue.builder().s("ITEM").build(),
                    "name", AttributeValue.builder().s("Libro: El Capital").build(),
                    "quantityRequested", AttributeValue.builder().n("2").build(),
                    "price", AttributeValue.builder().n("25").build()
            ));

            System.out.println("✓ 9 registros insertados correctamente");

        } catch (Exception e) {
            System.err.println("❌ Error insertando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertItem(Map<String, AttributeValue> item) {
        try {
            PutItemRequest request = PutItemRequest.builder()
                    .tableName("E-commerce")
                    .item(item)
                    .build();

            dynamoDbClient.putItem(request);
        } catch (Exception e) {
            System.err.println("❌ Error insertando item: " + e.getMessage());
            e.printStackTrace();
        }
    }
}