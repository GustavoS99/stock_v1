package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.utils.GlobalConstants;
import com.emazon.stock_v1.utils.Helpers;
import com.emazon.stock_v1.infraestructure.exception.CategoriesNotFoundException;
import com.emazon.stock_v1.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

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
    public List<Category> findAll(String sortDirection) {
        List<Category> categories;

        if (sortDirection == null || sortDirection.isEmpty()) {
            categories = categoryEntityMapper.categoryEntitiesToCategory(categoryRepository.findAll());
        } else {
            categories = categoryEntityMapper.categoryEntitiesToCategory(categoryRepository.findAll(
                    Helpers.buildSortForOneProperty(GlobalConstants.CATEGORY_SORT_BY, sortDirection)));
        }

        if(categories.isEmpty())
            throw new CategoriesNotFoundException();
        return categories;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name).map(categoryEntityMapper::categoryEntityToCategory);
    }
}
