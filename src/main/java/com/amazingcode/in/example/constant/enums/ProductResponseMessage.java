package com.amazingcode.in.example.constant.enums;

public enum ProductResponseMessage {
    PRODUCT_ALREADY_EXISTS("Product with name %s is already present."),
    PRODUCT_NOT_FOUND("Product with id %s does not exist."),
    PRODUCTS_NOT_PRESENT("Products not present."),
    FAILED_TO_CREATE_PRODUCT("Failed to create product with name: %s"),
    PRODUCT_SUCCESSFULLY_UPDATED("Product with id %s updated successfully."),
    PRODUCT_SUCCESSFULLY_DELETED("Product with id %s deleted successfully.");

    private final String message;

    ProductResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
