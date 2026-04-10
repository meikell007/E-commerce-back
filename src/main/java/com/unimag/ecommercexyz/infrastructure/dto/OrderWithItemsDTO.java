package com.unimag.ecommercexyz.infrastructure.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderWithItemsDTO {
    private String orderId;
    private String orderDate;
    private String orderStatus;
    private String shippingAddress;
    private String totalPrice;
    private List<ProductDTO> products;
}
