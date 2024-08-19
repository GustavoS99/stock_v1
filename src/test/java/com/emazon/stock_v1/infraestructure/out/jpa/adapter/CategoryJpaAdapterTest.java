package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
        category = new Category(null, "Electrónica", "Artículos electrónicos");
        categoryEntity = new CategoryEntity(null,"Electrónica", "Artículos electrónicos");
    }

    @Test
    void saveTest() {
        CategoryEntity savedCategoryEntity = new CategoryEntity(
                1L, "Electrónica", "Artículos Electrónicos");

        Category savedCategory = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryEntityMapper.categoryToCategoryEntity(any(Category.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedCategoryEntity);
        when(categoryEntityMapper.categoryEntityToCategory(any(CategoryEntity.class))).thenReturn(savedCategory);

        Category result = categoryJpaAdapter.save(category);

        assertEquals(savedCategory.getId(), result.getId());
        assertEquals(savedCategory.getName(), result.getName());
        assertNotNull(result, "El resultado no debería ser null");
    }

    @Test
    void save_shouldThrowCategoryAlreadyExistsException_whenCategoryExists() {
        CategoryEntity existingCategoryEntity = new CategoryEntity(
                1L, "Electrónica", "Artículos Electrónicos");

        Category existingCategory = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryRepository.findByName("Electrónica")).thenReturn(Optional.of(existingCategoryEntity));

        assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryJpaAdapter.save(existingCategory);
        });

        verify(categoryRepository, times(1)).findByName("Electrónica");
    }
}
