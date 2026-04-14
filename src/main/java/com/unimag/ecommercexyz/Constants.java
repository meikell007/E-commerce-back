package com.unimag.ecommercexyz;

public enum Constants {
    USER_PK_DB("USER#"),
    PROFILE_SK_DB("PROFILE"),
    ORDER_DB("ORDER#"),
    ITEM_TYPE_DB("ITEM");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
