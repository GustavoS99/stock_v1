package com.emazon.stock_v1.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CategoryRequest {
    @NotNull
    @NotEmpty
    @Size(max = 50, message = "The 'name' field is too long, it must have a maximum of 50 characters")
    private String name;
    @NotNull
    @NotEmpty
    @Size(max = 90, message = "The 'description' field is too long, it must have a maximum of 90 characters")
    private String description;
}
