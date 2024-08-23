package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {
    private final IBrandHandler brandHandler;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Brand already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Brand name or description is empty", content = @Content)
    })
    @Operation(summary = "Add a new brand")
    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody BrandRequest brandRequest) {
        brandHandler.save(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
