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
                                    .attributeName("PK")
                                    .keyType(KeyType.HASH)
                                    .build(),
                            KeySchemaElement.builder()
                                    .attributeName("SK")
                                    .keyType(KeyType.RANGE)
                                    .build()
                    )
                    .attributeDefinitions(
                            AttributeDefinition.builder()
                                    .attributeName("PK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("SK")
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

            // 1. USER_001 PROFILE
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("PROFILE").build(),
                    "nombre", AttributeValue.builder().s("Luisa").build(),
                    "correo", AttributeValue.builder().s("l@x.com").build(),
                    "direcciones", AttributeValue.builder()
                            .l(Arrays.asList(
                                    AttributeValue.builder().s("Calle 10, Bogotá").build(),
                                    AttributeValue.builder().s("Ave. 5, Medellín").build()
                            ))
                            .build(),
                    "pagos", AttributeValue.builder()
                            .l(Arrays.asList(
                                    AttributeValue.builder().s("Visa ...1234").build(),
                                    AttributeValue.builder().s("PayPal").build()
                            ))
                            .build()
            ));

            // 2. USER_001 ORD_556
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("ORD_556").build(),
                    "estado", AttributeValue.builder().s("Pago exitoso").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-11-15T14:30Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Calle 10, Bogotá").build(),
                    "total", AttributeValue.builder().n("300").build()
            ));

            // 3. USER_001 ORD_554
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("ORD_554").build(),
                    "estado", AttributeValue.builder().s("Enviado").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-11-01T09:15Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Calle 10, Bogotá").build(),
                    "total", AttributeValue.builder().n("80").build()
            ));

            // 4. USER_001 ORD_555
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("ORD_555").build(),
                    "estado", AttributeValue.builder().s("Pago exitoso").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-10-27T08:00Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Calle 10, Bogotá").build(),
                    "total", AttributeValue.builder().n("1250").build()
            ));

            // 5. USER_001 ORD_553
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("ORD_553").build(),
                    "estado", AttributeValue.builder().s("Enviado").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-10-10T11:45Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Ave. 5, Medellín").build(),
                    "total", AttributeValue.builder().n("150").build()
            ));

            // 6. USER_001 ORD_552
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("USER_001").build(),
                    "SK", AttributeValue.builder().s("ORD_552").build(),
                    "estado", AttributeValue.builder().s("Pago exitoso").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-09-25T16:00Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Ave. 5, Medellín").build(),
                    "total", AttributeValue.builder().n("200").build()
            ));

            // 7. ORD_555 DETAILS
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("ORD_555").build(),
                    "SK", AttributeValue.builder().s("DETAILS").build(),
                    "estado", AttributeValue.builder().s("Pago exitoso").build(),
                    "fechaCreacion", AttributeValue.builder().s("2023-10-27T08:00Z").build(),
                    "direccionEnvio", AttributeValue.builder().s("Calle 10, Bogotá").build(),
                    "total", AttributeValue.builder().n("1250").build()
            ));

            // 8. ORD_555 ITEM_001
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("ORD_555").build(),
                    "SK", AttributeValue.builder().s("ITEM_001").build(),
                    "producto", AttributeValue.builder().s("Laptop XPS").build(),
                    "cantidad", AttributeValue.builder().n("1").build(),
                    "precioUnitarioCompra", AttributeValue.builder().n("1200").build(),
                    "subtotal", AttributeValue.builder().n("1200").build()
            ));

            // 9. ORD_555 ITEM_002
            insertItem(Map.of(
                    "PK", AttributeValue.builder().s("ORD_555").build(),
                    "SK", AttributeValue.builder().s("ITEM_002").build(),
                    "producto", AttributeValue.builder().s("Libro: El Capital").build(),
                    "cantidad", AttributeValue.builder().n("2").build(),
                    "precioUnitarioCompra", AttributeValue.builder().n("25").build(),
                    "subtotal", AttributeValue.builder().n("50").build()
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