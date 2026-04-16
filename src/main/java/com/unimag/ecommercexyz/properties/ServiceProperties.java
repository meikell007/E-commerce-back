package com.unimag.ecommercexyz.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce.service")
public record ServiceProperties(
        int historyOrderLimit
) {
}
