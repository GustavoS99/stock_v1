package com.emazon.stock_v1.infraestructure.exceptionHandler;

public enum ExceptionResponse {

    CATEGORY_ALREADY_EXISTS("There is already a category with that name"),
    CATEGORY_NAME_TOO_LONG("The category name is too long"),
    CATEGORY_DESCRIPTION_TOO_LONG("The category description is too long"),;

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
