package com.emazon.stock_v1.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    private String name;
    private String description;
    private long quantity;
    private BigDecimal price;
    private BrandRequest brandRequest;
    private Set<CategoryRequest> categoryRequests;
}
