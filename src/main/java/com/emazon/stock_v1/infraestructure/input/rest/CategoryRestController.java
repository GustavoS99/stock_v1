package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.handler.ICategoryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
