package com.unimag.ecommercexyz.infrastructure.dto;

import lombok.Data;

@Data
public class OrderSummaryDTO {
    private String orderId;
    private String orderDate;
    private String orderStatus;
    private String shippingAddress;
}
