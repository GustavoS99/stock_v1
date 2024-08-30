package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    @Mapping(target = "CategoryResponse.id", ignore = true)
    PaginatedResult<CategoryResponse> categoriesToCategoryResponsePage(PaginatedResult<Category> categories);
}
