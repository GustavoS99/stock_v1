package com.emazon.stock_v1.infraestructure.out.jpa.mapper;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryEntityMapper.class);

    @Mapping(target = "items", ignore = true)
    CategoryEntity categoryToCategoryEntity(Category category);

    Category categoryEntityToCategory(CategoryEntity categoryEntity);
}
