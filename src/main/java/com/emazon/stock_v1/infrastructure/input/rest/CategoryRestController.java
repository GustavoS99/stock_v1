package com.emazon.stock_v1.infrastructure.input.rest;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.application.handler.ICategoryHandler;
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

import static com.emazon.stock_v1.infrastructure.input.rest.util.PathDefinition.CATEGORIES;
import static com.emazon.stock_v1.infrastructure.input.rest.util.PathDefinition.ROOT;

@RestController
@RequestMapping(CATEGORIES)
@RequiredArgsConstructor
@Validated
public class CategoryRestController {
    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Category name or description is empty", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping(ROOT)
    public ResponseEntity<Void> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all the categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All categories returned",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping(ROOT)
    public ResponseEntity<PaginatedResult<CategoryResponse>> findAll(
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = GlobalConstants.EMPTY_STRING) String sortDirection) {
        return ResponseEntity.ok(categoryHandler.findAll(page, size, sortDirection));
    }
}
