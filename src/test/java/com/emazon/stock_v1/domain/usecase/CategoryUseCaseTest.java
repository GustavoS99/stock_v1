package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("Should call categoryPersistencePort.save(category) one time")
    void saveTest() {

        Category savedCategory = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryPersistencePort.save(any(Category.class))).thenReturn(savedCategory);

        categoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

    @ParameterizedTest
    @DisplayName("Should validate calling of CategoryPersistencePort.findAll()")
    @CsvSource({
            "1, 2, asc",
            "2, 1, desc"
    })
    void findAllTest(int page, int size, String sort) {

        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(new Category(null, "Electrónica", "Artículos Electrónicos"));
        categoriesList.add(new Category(null, "Ropa y Moda", "Prendas de vestir"));
        categoriesList.add(new Category(null, "Hogar y Cocina", "Artículos para el hogar"));
        categoriesList.add(new Category(null, "Deportes y Aire Libre", "Equipos deportivos"));

        Page<Category> categories = new PageImpl<>(categoriesList);

        when(categoryPersistencePort.findAll(any(Pageable.class))).thenReturn(categories);

        categoryUseCase.findAll(page, size, sort);

        verify(categoryPersistencePort, times(1)).findAll(any(Pageable.class));
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, asc",
            "1, -2, asc"
    })
    void findAll_shouldThrowInvalidPaginationParametersException(int page, int size, String sort) {

        assertThrows(InvalidPaginationParametersException.class, () -> {
                categoryUseCase.findAll(page, size, sort);
        });

    }
}
