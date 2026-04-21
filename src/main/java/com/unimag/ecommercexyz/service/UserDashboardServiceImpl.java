package com.unimag.ecommercexyz.service;

import com.unimag.ecommercexyz.dto.OrderDetailsDTO;
import com.unimag.ecommercexyz.dto.ProfileDTO;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.OrderItemsAggregate;
import com.unimag.ecommercexyz.entity.UserEntity;
import com.unimag.ecommercexyz.exception.UserNotFoundException;
import com.unimag.ecommercexyz.mapper.UserDashboardMapper;
import com.unimag.ecommercexyz.properties.ServiceProperties;
import com.unimag.ecommercexyz.repository.UserDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDashboardServiceImpl implements UserDashboardService {

    private final UserDashboardRepository repository;
    private final ServiceProperties properties;
    private final UserDashboardMapper userDashboardMapper;
    private static final Logger log = LoggerFactory.getLogger(UserDashboardServiceImpl.class);

    @Override
    public ProfileDTO findProfileAndOrderHistory(String userId) {
        log.info("CACHE MISS - searching profile and order history in DynamoDB {}", userId);
        UserEntity user = repository.findUserProfile(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        List<OrderEntity> orders = repository.findLastOrders(userId, properties.historyOrderLimit());

        return userDashboardMapper.userAndOrdersToProfileDTO(user, orders);
    }

    @Cacheable(value = "orders", key = "#orderId")
    @Override
    public OrderDetailsDTO findOrderWithItems(String orderId) {
        log.info("CACHE MISS - searching order in DynamoDB {}", orderId);
        OrderItemsAggregate orderWithItems = repository.findOrderDetails(orderId);

        return userDashboardMapper.orderAndItemsToOrderDetailsDTO(orderWithItems);
    }

}
