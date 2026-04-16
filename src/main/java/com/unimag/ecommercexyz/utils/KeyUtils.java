package com.unimag.ecommercexyz.utils;


public class KeyUtils {

    public static String extractId(String pk, String prefix) {
        System.out.println("------prefijo: " + prefix);
        System.out.println("------pk order: " + pk);
        if (pk == null || !pk.startsWith(prefix)) {
            throw new IllegalArgumentException("Invalid USER pk: " + pk);
        }
        return pk.substring(prefix.length());
    }
}
