package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Electrónica", "Artículos Electrónicos");
    }

    @Test
    void saveTest() {

        Category savedCategory = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryPersistencePort.save(any(Category.class))).thenReturn(savedCategory);

        categoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }
}
