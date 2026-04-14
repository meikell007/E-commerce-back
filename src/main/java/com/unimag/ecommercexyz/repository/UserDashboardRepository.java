package com.unimag.ecommercexyz.repository;

import com.unimag.ecommercexyz.entity.ItemEntity;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.UserEntity;

import java.util.List;

public interface UserDashboardRepository {
    UserEntity getUserProfile(String userId);
    List<OrderEntity> getLastOrders(String userId, int limit);
    List<ItemEntity> getItemsByOrder(String orderId);
}
