package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllCategoriesUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private FindAllCategoriesUseCase findAllCategoriesUseCase;

    @ParameterizedTest
    @DisplayName("Should validate calling of CategoryPersistencePort.findAll()")
    @CsvSource({
            "1, 2, asc",
            "2, 1, desc"
    })
    void findAllTest(int page, int size, String sort) {

        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(new Category(1L, "Electrónica", "Artículos Electrónicos"));
        categoriesList.add(new Category(2L, "Ropa y Moda", "Prendas de vestir"));
        categoriesList.add(new Category(3L, "Hogar y Cocina", "Artículos para el hogar"));
        categoriesList.add(new Category(4L, "Deportes y Aire Libre", "Equipos deportivos"));

        Page<Category> categories = new PageImpl<>(categoriesList);

        when(categoryPersistencePort.findAll(any(Pageable.class))).thenReturn(categories);

        findAllCategoriesUseCase.findAll(page, size, sort);

        verify(categoryPersistencePort, times(1)).findAll(any(Pageable.class));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidPaginationParametersException when page or size are wrong")
    @CsvSource({
            "-1, 2, asc",
            "1, -2, asc"
    })
    void findAll_shouldThrowInvalidPaginationParametersException(int page, int size, String sort) {

        assertThrows(InvalidPaginationParametersException.class, () -> {
            findAllCategoriesUseCase.findAll(page, size, sort);
        });

    }
}
