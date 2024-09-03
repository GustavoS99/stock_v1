package com.emazon.stock_v1.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ItemResponse {
    private String name;
    private String description;
    private long quantity;
    private BigDecimal price;
    private BrandResponse brand;
    private Set<CategoryItemResponse> categories;
}
