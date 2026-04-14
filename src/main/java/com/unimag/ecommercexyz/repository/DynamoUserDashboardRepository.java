package com.unimag.ecommercexyz.repository;

import com.unimag.ecommercexyz.Constants;
import com.unimag.ecommercexyz.entity.ItemEntity;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DynamoUserDashboardRepository implements UserDashboardRepository {

    private final DynamoDbTable<UserEntity> userTable;
    private final DynamoDbTable<OrderEntity> orderTable;
    private final DynamoDbTable<ItemEntity> itemTable;

    @Override
    public UserEntity getUserProfile(String userId) {
        return userTable.getItem(Key.builder()
                .partitionValue(Constants.USER_PK_DB.getValue() + userId)
                .sortValue(Constants.PROFILE_SK_DB.getValue())
                .build());
    }

    @Override
    public List<OrderEntity> getLastOrders(String userId, int limit) {

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.sortBeginsWith(
                                Key.builder()
                                        .partitionValue(Constants.USER_PK_DB.getValue() + userId)
                                        .sortValue(Constants.ORDER_DB.getValue())
                                        .build()
                        )
                )
                .scanIndexForward(false)
                .limit(limit)
                .build();

        return orderTable.query(request)
                .items()
                .stream()
                .toList();
    }

    @Override
    public List<ItemEntity> getItemsByOrder(String orderId) {

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.keyEqualTo(
                                Key.builder()
                                        .partitionValue(Constants.ORDER_DB.getValue() + orderId)
                                        .build()
                        )
                )
                .build();

        return itemTable.query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .filter(item -> Constants.ITEM_TYPE_DB.getValue().equals(item.getType()))
                .toList();
    }
}
