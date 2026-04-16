package com.unimag.ecommercexyz.repository;

import com.unimag.ecommercexyz.entity.*;
import com.unimag.ecommercexyz.exception.OrderNotFoundException;
import com.unimag.ecommercexyz.mapper.UserDashboardMapper;
import com.unimag.ecommercexyz.properties.DbProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDashboardRepositoryImpl implements UserDashboardRepository {

    private final DynamoDbTable<UserEntity> userAccessTable;
    private final DynamoDbTable<OrderEntity> orderAccessTable;
    private final DynamoDbTable<OrderItemEntity> orderItemAccessTable;
    private final DbProperties properties;
    private final UserDashboardMapper mapper;

    @Override
    public Optional<UserEntity> findUserProfile(String userId) {
        String pk = properties.userPkPrefix() + userId;
        System.out.println("----- esta es la pk" + pk);
        return Optional.ofNullable(userAccessTable.getItem(
                Key.builder()
                .partitionValue(pk)
                .sortValue(properties.profileSk())
                .build()));
    }

    @Override
    public List<OrderEntity> findLastOrders(String userId, int limit) {

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.sortBeginsWith(
                                Key.builder()
                                        .partitionValue(properties.userPkPrefix() + userId)
                                        .sortValue(properties.orderPrefix())
                                        .build()
                        )
                )
                .scanIndexForward(false)
                .limit(limit)
                .build();

        return orderAccessTable.query(request)
                .items()
                .stream()
                .toList();
    }

    @Override
    public OrderItemsAggregate findOrderDetails(String orderId) {

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.keyEqualTo(
                                Key.builder()
                                        .partitionValue(properties.orderPrefix() + orderId)
                                        .build()
                        )
                )
                .build();

        List<OrderItemEntity> rows = orderItemAccessTable.query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .toList();

        OrderEntity order = rows.stream()
                .filter(row -> properties.orderDetailsSk().equals(row.getSk()))
                .findFirst()
                .map(mapper::toOrderEntity)
                .orElseThrow(() -> new OrderNotFoundException("Order details not found."));

        List<ItemEntity> items = rows.stream()
                .filter(row -> properties.itemType().equals(row.getType()))
                .map(mapper::toItemEntity)
                .toList();

        return new OrderItemsAggregate(order, items);
    }

}
