package com.unimag.ecommercexyz.dto;



import java.util.List;

public record OrderDetailsDTO(
        OrderDTO order,
        Double totalPrice,
        List<ItemDTO> items
) {}
