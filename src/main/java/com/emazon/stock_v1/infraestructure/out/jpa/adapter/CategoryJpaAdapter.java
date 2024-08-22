package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.CategoriesNotFoundException;
import com.emazon.stock_v1.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable)
                .map(categoryEntityMapper::categoryEntityToCategory);
        if(categories.isEmpty())
            throw new CategoriesNotFoundException();
        return categories;
    }
}
