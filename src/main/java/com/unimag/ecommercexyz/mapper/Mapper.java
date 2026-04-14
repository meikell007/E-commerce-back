package com.unimag.ecommercexyz.mapper;

import com.unimag.ecommercexyz.Constants;
import com.unimag.ecommercexyz.dto.ItemDTO;
import com.unimag.ecommercexyz.dto.OrderSummaryDTO;
import com.unimag.ecommercexyz.dto.OrderWithItemsDTO;
import com.unimag.ecommercexyz.dto.ProfileDTO;
import com.unimag.ecommercexyz.entity.ItemEntity;
import com.unimag.ecommercexyz.entity.OrderEntity;
import com.unimag.ecommercexyz.entity.UserEntity;

import com.unimag.ecommercexyz.utils.GeneralUtils;
import com.unimag.ecommercexyz.utils.KeyUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Mapper {
    public ProfileDTO userAndOrdersToDTO(UserEntity user, List<OrderEntity> orders) {
        List<OrderSummaryDTO> orderSummariesDTO = orders.stream()
                .map(orderEntity -> new OrderSummaryDTO(KeyUtils.extractId(orderEntity.getPk(), Constants.ORDER_DB.getValue()),
                        orderEntity.getCreationDate(), orderEntity.getStatus(), orderEntity.getShippingAddress()))
                .toList();

        ProfileDTO profileDTO = new ProfileDTO(KeyUtils.extractId(user.getPk(), Constants.USER_PK_DB.getValue()), user.getName(), user.getEmail(),
                user.getShippingAddress(), user.getPaymentMethod(), orderSummariesDTO);

        return profileDTO;
    }

    public OrderWithItemsDTO orderAndItemsToDTO(OrderEntity order, List<ItemEntity> items) {
        List<ItemDTO> itemsDTO = items.stream()
                .map(itemEntity -> new ItemDTO(itemEntity.getName(), itemEntity.getPrice(), itemEntity.getQuantityRequested()))
                .toList();

        OrderWithItemsDTO orderWithItemsDTO = new OrderWithItemsDTO(KeyUtils.extractId(order.getPk(),
                Constants.ORDER_DB.getValue()), order.getCreationDate(), order.getStatus(),
                order.getShippingAddress(), GeneralUtils.getTotalPrice(itemsDTO), itemsDTO);

        return orderWithItemsDTO;
    }
   
}
