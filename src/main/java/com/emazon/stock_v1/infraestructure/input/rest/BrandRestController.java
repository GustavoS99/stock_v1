package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.application.handler.IBrandHandler;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.utils.GlobalConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Validated
public class BrandRestController {
    private final IBrandHandler brandHandler;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Brand already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Brand name or description is empty", content = @Content)
    })
    @Operation(summary = "Add a new brand")
    @PostMapping("/")
    public ResponseEntity<Void> save(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.save(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all the brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All brands returned",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = BrandResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedResult<BrandResponse>> findAll(
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = GlobalConstants.EMPTY_STRING) String sortDirection) {
        return ResponseEntity.ok(brandHandler.findAll(page, size, sortDirection));
    }
}
