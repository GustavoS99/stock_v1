package com.emazon.stock_v1.constants;

public class GlobalConstants {

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int START_PAGE = 0;
    public static final int START_PAGE_SIZE = 0;
    public static final String SORT_BY = "name";
    public static final String DESCENDING_SORT = "desc";
    public static final String ASCENDING_SORT = "asc";
    public static final int LENGTH_CATEGORY_NAME = 50;
    public static final int LENGTH_CATEGORY_DESCRIPTION = 90;

    private GlobalConstants() {
        throw new IllegalStateException("Utility class");
    }
}
