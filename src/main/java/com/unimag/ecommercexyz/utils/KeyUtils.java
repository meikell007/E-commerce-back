package com.unimag.ecommercexyz.utils;


public class KeyUtils {

    public static String extractId(String pk, String prefix) {
        if (pk == null || !pk.startsWith(prefix)) {
            throw new IllegalArgumentException("Invalid USER pk: " + pk);
        }
        return pk.substring(prefix.length());
    }
}
