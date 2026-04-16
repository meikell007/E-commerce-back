package com.unimag.ecommercexyz.dto;

public record OrderDTO(
        String id,
        String creationDate,
        String status,
        String shippingAddress
) {}
