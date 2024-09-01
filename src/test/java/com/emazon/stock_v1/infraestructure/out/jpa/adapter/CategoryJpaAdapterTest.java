package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    static CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    private CategoryEntity categoryEntity;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Electrónica", "Artículos electrónicos");
        categoryEntity = new CategoryEntity(category.getId(),category.getName(), category.getDescription());
    }

    @Test
    void when_saveCategory_expect_fieldsSavedSuccessfully() {
        CategoryEntity savedCategoryEntity = new CategoryEntity(
                category.getId(), category.getName(), category.getDescription());

        Category savedCategory = new Category(category.getId(), category.getName(), category.getDescription());

        when(categoryEntityMapper.categoryToCategoryEntity(any(Category.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedCategoryEntity);
        when(categoryEntityMapper.categoryEntityToCategory(any(CategoryEntity.class))).thenReturn(savedCategory);

        Category result = categoryJpaAdapter.save(category);

        assertNotNull(result, "The result should not be null");
        assertEquals(savedCategory.getId(), result.getId());
        assertEquals(savedCategory.getName(), result.getName());
        assertEquals(savedCategory.getDescription(), result.getDescription());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "''",
            "desc",
            "asc"
    }, nullValues = {"null"})
    void when_findAll_expect_ListReturnedSuccessfully(String sortDirection){

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(categoryEntity);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        if(sortDirection == null || sortDirection.isEmpty()){
            when(categoryRepository.findAll()).thenReturn(categoryEntities);
        } else {
            when(categoryRepository.findAll(any(Sort.class))).thenReturn(categoryEntities);
        }

        when(categoryEntityMapper.categoryEntitiesToCategory(anyList())).thenReturn(categories);

        List<Category> result = categoryJpaAdapter.findAll(sortDirection);

        assertEquals(categories, result);

        if(sortDirection == null || sortDirection.isEmpty()) {
            verify(categoryRepository, times(1)).findAll();
        } else {
            verify(categoryRepository, times(1)).findAll(any(Sort.class));
        }
    }
}
