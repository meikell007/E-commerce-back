package com.unimag.ecommercexyz.mapper;

import com.unimag.ecommercexyz.dto.ItemDTO;
import com.unimag.ecommercexyz.dto.OrderDTO;
import com.unimag.ecommercexyz.dto.OrderDetailsDTO;
import com.unimag.ecommercexyz.dto.ProfileDTO;
import com.unimag.ecommercexyz.entity.*;

import com.unimag.ecommercexyz.properties.DbProperties;
import com.unimag.ecommercexyz.utils.GeneralUtils;
import com.unimag.ecommercexyz.utils.KeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserDashboardMapper {

    private final DbProperties properties;

    public ProfileDTO userAndOrdersToProfileDTO(UserEntity user, List<OrderEntity> orders) {
        List<OrderDTO> orderSummariesDTO = orders.stream()
                .map(orderEntity -> new OrderDTO(KeyUtils.extractId(orderEntity.getPk(), properties.orderPrefix()),
                        orderEntity.getCreationDate(), orderEntity.getStatus(), orderEntity.getShippingAddress()))
                .toList();

        ProfileDTO profileDTO = new ProfileDTO(KeyUtils.extractId(user.getPk(), properties.userPkPrefix()), user.getName(), user.getEmail(),
                user.getShippingAddress(), user.getPaymentMethod(), orderSummariesDTO);

        return profileDTO;
    }

    public OrderDetailsDTO orderAndItemsToOrderDetailsDTO(OrderItemsAggregate orderItemsAggregate) {
        OrderEntity order = orderItemsAggregate.getOrder();
        List<ItemEntity> items = orderItemsAggregate.getItems();

        OrderDTO orderDTO = new OrderDTO(KeyUtils.extractId(order.getPk(), properties.orderPrefix()), order.getCreationDate(), order.getStatus(), order.getShippingAddress());
        List<ItemDTO> itemsDTO = items.stream()
                .map(itemEntity -> new ItemDTO(itemEntity.getName(), itemEntity.getPrice(), itemEntity.getQuantityRequested()))
                .toList();

        return new OrderDetailsDTO(orderDTO, GeneralUtils.getTotalPrice(itemsDTO), itemsDTO);
    }

    public OrderEntity toOrderEntity(OrderItemEntity row) {
        return OrderEntity.builder()
                .pk(row.getPk())
                .sk(row.getSk())
                .type(row.getType())
                .creationDate(row.getCreationDate())
                .status(row.getStatus())
                .shippingAddress(row.getShippingAddress())
                .build();
    }

    public ItemEntity toItemEntity(OrderItemEntity row) {
        return ItemEntity.builder()
                .pk(row.getPk())
                .sk(row.getSk())
                .type(row.getType())
                .name(row.getName())
                .price(row.getPrice())
                .quantityRequested(row.getQuantityRequested())
                .build();
    }
   
}
