package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponseMapper INSTANCE = Mappers.getMapper(CategoryResponseMapper.class);

    CategoryResponse categoryToCategoryResponse(Category category);
}
