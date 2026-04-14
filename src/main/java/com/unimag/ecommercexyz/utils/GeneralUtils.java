package com.unimag.ecommercexyz.utils;

import com.unimag.ecommercexyz.dto.ItemDTO;

import java.util.List;

public class GeneralUtils {

    public static Double getTotalPrice(List<ItemDTO> itemsDTO) {
        return itemsDTO.stream()
                .mapToDouble(itemDTO -> {
                    double price = Double.parseDouble(itemDTO.price());
                    double quantity = Double.parseDouble(itemDTO.quantityRequested());
                    return price * quantity;
                })
                .sum();
    }
}
