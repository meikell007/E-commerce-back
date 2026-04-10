package com.unimag.ecommercexyz.infrastructure.dto;

import lombok.Data;

@Data
public class ProfileDTO {
    private String userId;
    private String username;
    private String email;
    private String shippingAddresses;
    private String paymentMethods;
}
