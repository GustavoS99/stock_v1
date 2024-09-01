package com.emazon.stock_v1.infraestructure.exceptionhandler;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.infraestructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException categoryAlreadyExistsException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriesNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoriesNotFoundException(
            CategoriesNotFoundException categoriesNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORIES_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(InvalidPaginationParametersException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPaginationParametersException(
            InvalidPaginationParametersException invalidPaginationParametersException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_PAGINATION_PARAMETER.getMessage()));
    }

    @ExceptionHandler(EmptyCategoryNameException.class)
    public ResponseEntity<Map<String, String>> handleEmptyCategoryNameException(
            EmptyCategoryNameException emptyCategoryNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_NAME.getMessage()));
    }

    @ExceptionHandler(EmptyCategoryDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleEmptyCategoryDescriptionException(
            EmptyCategoryDescriptionException emptyCategoryDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_DESCRIPTION.getMessage()));
    }

    @ExceptionHandler(EmptyBrandNameException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBrandNameException(
            EmptyBrandNameException emptyBrandNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_NAME.getMessage()));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBrandAlreadyExistsException(
            BrandAlreadyExistsException brandAlreadyExistsException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRAND_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(BrandsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandsNotFoundException(
            BrandsNotFoundException brandsNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRANDS_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleItemAlreadyExistsException(
            ItemAlreadyExistsException itemAlreadyExistsException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ITEM_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(InvalidLengthItemNameException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLengthItemNameException(
            InvalidLengthItemNameException invalidLengthItemNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_LENGTH_NAME.getMessage()));
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFoundException(
            BrandNotFoundException brandNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRAND_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException categoryNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(EmptyBrandOfItemException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBrandOfItemException(
            EmptyBrandOfItemException emptyBrandOfItemException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_BRAND_OF_ITEM.getMessage()));
    }

    @ExceptionHandler(InvalidNumOfCategoriesException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNumOfCategories(
            InvalidNumOfCategoriesException invalidNumOfCategoriesException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_NUM_OF_CATEGORIES.getMessage()));
    }

    @ExceptionHandler(ItemHasDuplicateCategories.class)
    public ResponseEntity<Map<String, String>> handleItemHasDuplicateCategories(
            ItemHasDuplicateCategories itemHasDuplicateCategories
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ITEM_HAS_DUPLICATE_CATEGORIES.getMessage()));
    }

    @ExceptionHandler(EmptyItemNameException.class)
    public ResponseEntity<Map<String, String>> handleEmptyItemNameException(
            EmptyItemNameException emptyItemNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_NAME.getMessage()));
    }

    @ExceptionHandler(EmptyItemDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleEmptyItemDescriptionException(
            EmptyItemDescriptionException emptyItemDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_DESCRIPTION.getMessage()));
    }

    @ExceptionHandler(ItemsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleItemsNotFoundException(
            ItemsNotFoundException itemsNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ITEMS_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(PageExceedTotalPagesException.class)
    public ResponseEntity<Map<String, String>> handlePageExceedTotalPagesException(
            PageExceedTotalPagesException pageExceedTotalPagesException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PAGE_EXCEED_PAGES.getMessage()));
    }
}
