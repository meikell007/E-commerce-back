package com.unimag.ecommercexyz.dto;

public record OrderSummaryDTO (
        String id,
        String creationDate,
        String status,
        String shippingAddress
) {}
