package com.unimag.ecommercexyz.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemsAggregate {
    private OrderEntity order;
    private List<ItemEntity> items;

}
