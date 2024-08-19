package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category save(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        }
        CategoryEntity categoryEntity = categoryEntityMapper.categoryToCategoryEntity(category);
        CategoryEntity result = categoryRepository.save(categoryEntity);
        return categoryEntityMapper.categoryEntityToCategory(result);
    }

}
