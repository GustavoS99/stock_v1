package com.emazon.stock_v1.infraestructure.exceptionhandler;

public enum ExceptionResponse {

    CATEGORY_ALREADY_EXISTS("There is already a category with that name."),
    CATEGORIES_NOT_FOUND("No categories found."),
    INVALID_PAGINATION_PARAMETER("Invalid page or size parameter."),
    BRAND_ALREADY_EXISTS("There is already a brand with that name."),
    BRANDS_NOT_FOUND("No brands found"),
    ITEM_ALREADY_EXISTS("There is already a item with that name"),
    EMPTY_NAME("The name field is empty"),
    EMPTY_DESCRIPTION("The description field is empty"),
    INVALID_LENGTH_NAME("The name field too long. It must have a maximum of 50 characters."),

    INVALID_LENGTH_CATEGORY_DESCRIPTION(
            "The description field is too long. It must have a maximum of 90 characters."),
    INVALID_LENGTH_BRAND_DESCRIPTION(
            "The description field is too long. It must have a maximum of 150 characters."),
    INVALID_LENGTH_ITEM_DESCRIPTION(
            "The description field is too long. It must have a 200 characters."),
    BRAND_NOT_FOUND("The brand with that name does not exist."),
    CATEGORY_NOT_FOUND("The category with that name does not exist."),
    EMPTY_BRAND_OF_ITEM("The given brand is empty"),
    INVALID_NUM_OF_CATEGORIES(
            "The num of categories is invalid. It must have a minimum of 1 and a maximum of 3 categories."),
    ITEM_HAS_DUPLICATE_CATEGORIES("The given categories are duplicated"),
    ITEMS_NOT_FOUND("No items found"),
    PAGE_EXCEED_PAGES("The given page does not exists");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
