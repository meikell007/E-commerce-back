package com.unimag.ecommercexyz.repository;

import com.unimag.ecommercexyz.entity.OrderItemEntity;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.OrderItemsAggregate;
import com.unimag.ecommercexyz.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDashboardRepository {
    Optional<UserEntity> findUserProfile(String userId);
    List<OrderEntity> findLastOrders(String userId, int limit);
    OrderItemsAggregate findOrderDetails(String orderId);
}
