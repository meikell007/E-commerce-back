package com.unimag.ecommercexyz.infrastructure.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String productName;
    private String productPrice;
    private String productQuantity;
    private String subtotalPrice;
}
