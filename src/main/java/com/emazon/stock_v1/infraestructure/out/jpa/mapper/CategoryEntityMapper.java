package com.emazon.stock_v1.infraestructure.out.jpa.mapper;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntity categoryToCategoryEntity(Category category);

    Category categoryEntityToCategory(CategoryEntity categoryEntity);

}
