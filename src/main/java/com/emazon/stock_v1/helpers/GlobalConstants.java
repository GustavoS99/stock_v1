package com.emazon.stock_v1.helpers;

public class GlobalConstants {

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int START_PAGE = 0;
    public static final int START_PAGE_SIZE = 0;
    public static final String CATEGORY_SORT_BY = "name";
    public static final String DESCENDING_SORT = "desc";
    public static final String ASCENDING_SORT = "asc";
    public static final int LENGTH_CATEGORY_NAME = 50;
    public static final int LENGTH_CATEGORY_DESCRIPTION = 90;
    public static final int LENGTH_BRAND_DESCRIPTION = 150;
    public static final int LENGTH_BRAND_NAME = 50;
    public static final String BRAND_SORT_BY = "name";

    private GlobalConstants() {
        throw new IllegalStateException("Utility class");
    }
}
