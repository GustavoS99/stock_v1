package com.emazon.stock_v1.infrastructure.out.jpa.mapper;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    @Mapping(target = "items", ignore = true)
    CategoryEntity categoryToCategoryEntity(Category category);

    Category categoryEntityToCategory(CategoryEntity categoryEntity);

    List<Category> categoryEntitiesToCategory(List<CategoryEntity> categoryEntities);
}
