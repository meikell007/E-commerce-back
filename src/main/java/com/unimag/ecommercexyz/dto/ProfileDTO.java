package com.unimag.ecommercexyz.dto;

import java.util.List;


public record ProfileDTO (
        String id,
        String username,
        String email,
        String shippingAddresses,
        String paymentMethods,
        List<OrderSummaryDTO> recentOrders
) {}
