package com.amazingcode.in.example.constant.enums;

public enum CategoryResponseMessage {
    CATEGORY_ALREADY_EXISTS("Category with name '%s' already exists"),
    CATEGORY_NOT_FOUND("Category with id %d does not exist"),
    CATEGORY_NOT_PRESENT("Categories not present"),
    CATEGORY_CREATION_FAILED("Failed to create category with name '%s'"),
    CATEGORY_UPDATE_FAILED("Failed to update category with id %d"),
    CATEGORY_DELETION_FAILED("Failed to delete category with id %d"),
    CATEGORY_SUCCESSFULLY_CREATED("Category with name '%s' created successfully"),
    CATEGORY_SUCCESSFULLY_UPDATED("Category with id %d updated successfully"),
    CATEGORY_SUCCESSFULLY_DELETED("Category with id %d deleted successfully");

    private final String message;

    CategoryResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
