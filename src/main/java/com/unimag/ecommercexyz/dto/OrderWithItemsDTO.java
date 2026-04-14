package com.unimag.ecommercexyz.dto;



import java.util.List;

public record OrderWithItemsDTO (
        String id,
        String creationDate,
        String status,
        String shippingAddress,
        Double totalPrice,
        List<ItemDTO> items
) {}
