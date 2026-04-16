package com.unimag.ecommercexyz.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce.db")
public record DbProperties(
        String userPkPrefix,
        String profileSk,
        String orderPrefix,
        String itemType,
        String orderDetailsSk
)
{}
