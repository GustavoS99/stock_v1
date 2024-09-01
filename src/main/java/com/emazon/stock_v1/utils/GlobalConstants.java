package com.emazon.stock_v1.utils;

public class GlobalConstants {

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int START_PAGE = 0;
    public static final int START_PAGE_SIZE = 0;
    public static final String CATEGORY_SORT_BY = "name";
    public static final int LENGTH_CATEGORY_NAME = 50;
    public static final int LENGTH_CATEGORY_DESCRIPTION = 90;
    public static final int LENGTH_BRAND_DESCRIPTION = 150;
    public static final int LENGTH_BRAND_NAME = 50;
    public static final String BRAND_SORT_BY = "name";
    public static final int LENGTH_ITEM_NAME = 50;
    public static final int LENGTH_ITEM_DESCRIPTION = 200;
    public static final int ITEM_MIN_CATEGORIES = 1;
    public static final int ITEM_MAX_CATEGORIES = 3;
    public static final String ITEM_SORT_BY = "name";
    public static final String CATEGORY = "CATEGORY";
    public static final String ITEMS_SORT_BY_CATEGORY = "categories.name";
    public static final String BRAND = "BRAND";
    public static final String ITEMS_SORT_BY_BRAND = "brand.name";
    public static final String EMPTY_STRING = "";
    public static final int MIN_LEN_NAME = 1;
    public static final String BAD_NAME_LENGTH_MESSAGE =
            "The name field is too long. It must have a maximum of 50 characters.";
    public static final int MIN_LEN_DESCRIPTION = 1;
    public static final String BAD_CATEGORY_DESCRIPTION_LENGTH_MESSAGE =
            "The description field is too long. It must have a maximum of 90 characters.";
    public static final String EMPTY_NAME_MESSAGE = "The name field is empty";
    public static final String NULL_NAME_MESSAGE = "The name field is null";
    public static final String EMPTY_DESCRIPTION_MESSAGE = "The description field is empty";
    public static final String NULL_DESCRIPTION_MESSAGE = "The description field is null";
    public static final String BAD_BRAND_DESCRIPTION_LENGTH_MESSAGE =
            "The description field is too long. It must have a maximum of 150 characters.";
    public static final String BAD_ITEM_DESCRIPTION_LENGTH =
            "The description field is too long. It must have a maximum of 200 characters.";
    public static final String EMPTY_BRAND_OF_ITEM_MESSAGE = "The brand is empty";
    public static final String EMPTY_CATEGORIES_OF_ITEM_MESSAGE = "The categories are empty";
    public static final String ASCENDING_SORT = "ASC";

    private GlobalConstants() {
        throw new IllegalStateException("Utility class");
    }
}
