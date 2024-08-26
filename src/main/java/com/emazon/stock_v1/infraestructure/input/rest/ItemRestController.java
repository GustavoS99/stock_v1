package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.handler.IItemHandler;
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
}
