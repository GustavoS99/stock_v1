package com.emazon.stock_v1.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CategoryResponse {
    private long id;
    private String name;
    private String description;
}
