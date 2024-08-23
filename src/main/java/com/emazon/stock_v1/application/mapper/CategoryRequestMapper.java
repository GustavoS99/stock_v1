package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category categoryRequestToCategory(CategoryRequest categoryRequest);
}
