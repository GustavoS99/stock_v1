package com.emazon.stock_v1.infraestructure.exceptionhandler;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.exception.CategoriesNotFoundException;
import com.emazon.stock_v1.infraestructure.exception.CategoryAlreadyExistsException;
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

    @ExceptionHandler(InvalidLengthCategoryNameException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLengthCategoryNameException(
            InvalidLengthCategoryNameException invalidLengthCategoryNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_LENGTH_CATEGORY_NAME.getMessage()));
    }

    @ExceptionHandler(InvalidLengthCategoryDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLengthCategoryDescriptionException(
            InvalidLengthCategoryDescriptionException invalidLengthCategoryDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(
                        MESSAGE, ExceptionResponse.INVALID_LENGTH_DESCRIPTION_NAME.getMessage()));
    }

    @ExceptionHandler(EmptyCategoryNameException.class)
    public ResponseEntity<Map<String, String>> handleEmptyCategoryNameException(
            EmptyCategoryNameException emptyCategoryNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_CATEGORY_NAME.getMessage()));
    }

    @ExceptionHandler(EmptyCategoryDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleEmptyCategoryDescriptionException(
            EmptyCategoryDescriptionException emptyCategoryDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_CATEGORY_DESCRIPTION.getMessage()));
    }

    @ExceptionHandler(EmptyBrandNameException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBrandNameException(
            EmptyBrandNameException emptyBrandNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_BRAND_NAME.getMessage()));
    }

    @ExceptionHandler(EmptyBrandDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBrandDescriptionException(
            EmptyBrandDescriptionException emptyBrandDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_BRAND_DESCRIPTION.getMessage()));
    }

    @ExceptionHandler(InvalidLengthBrandNameException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLengthBrandNameException(
            InvalidLengthBrandNameException invalidLengthBrandNameException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_LENGTH_BRAND_NAME.getMessage()));
    }

    @ExceptionHandler(InvalidLengthBrandDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLengthBrandDescriptionException(
            InvalidLengthBrandDescriptionException invalidLengthBrandDescriptionException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(
                        MESSAGE, ExceptionResponse.INVALID_LENGTH_BRAND_DESCRIPTION.getMessage()));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBrandAlreadyExistsException(
            BrandAlreadyExistsException brandAlreadyExistsException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRAND_ALREADY_EXISTS.getMessage()));
    }
}
