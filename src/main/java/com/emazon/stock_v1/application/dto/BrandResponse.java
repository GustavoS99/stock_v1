package com.emazon.stock_v1.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BrandResponse {
    private String name;
    private String description;
}
