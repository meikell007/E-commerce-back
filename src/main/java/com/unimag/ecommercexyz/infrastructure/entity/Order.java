package com.unimag.ecommercexyz.infrastructure.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String pk;
    private String userId;
    private List<QuantityByProduct> productsList;
    private String shippingAddress;
    private String status; // e.g., "PENDING", "SHIPPED", "DELIVERED"
}
