package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.dto.ItemResponse;
import com.emazon.stock_v1.application.handler.IItemHandler;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.utils.GlobalConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemRestController {
    private final IItemHandler itemHandler;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already exists", content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "Item name or description is empty or maybe too long", content = @Content)
    })
    @Operation(summary = "Add a new item")
    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody ItemRequest itemRequest) {
        itemHandler.save(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All items returned",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad filter parameter", content = @Content)
    })
    @Operation(summary = "Get all the items")
    @GetMapping("/")
    public ResponseEntity<PaginatedResult<ItemResponse>> findAll(
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = GlobalConstants.EMPTY_STRING) String sortBy,
            @RequestParam(defaultValue = GlobalConstants.ASCENDING_SORT) String sortDirection
    ) {
        return ResponseEntity.ok(itemHandler.findAll(page, size, sortBy, sortDirection));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All items returned by category",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad filter parameter", content = @Content)
    })
    @Operation(summary = "Get all the items by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<PaginatedResult<ItemResponse>> findByCategory(
            @PathVariable Long category,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = GlobalConstants.EMPTY_STRING) String sortBy,
            @RequestParam(defaultValue = GlobalConstants.ASCENDING_SORT) String sortDirection
    ) {
        return ResponseEntity.ok(itemHandler.findByCategory(category, page, size, sortBy, sortDirection));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All items returned by brand",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad filter parameter", content = @Content)
    })
    @Operation(summary = "Get all the items by brand")
    @GetMapping("/brand/")
    public ResponseEntity<PaginatedResult<ItemResponse>> findByBrand(
            @RequestBody BrandRequest brand,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = GlobalConstants.EMPTY_STRING) String sortBy,
            @RequestParam(defaultValue = GlobalConstants.ASCENDING_SORT) String sortDirection
    ) {
        return ResponseEntity.ok(itemHandler.findByBrandName(brand.getName(), page, size, sortBy, sortDirection));
    }
}
