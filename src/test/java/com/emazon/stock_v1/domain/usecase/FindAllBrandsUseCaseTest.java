package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
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
class FindAllBrandsUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private FindAllBrandsUseCase findAllBrandsUseCase;

    @ParameterizedTest
    @DisplayName("Should validate calling of CategoryPersistencePort.findAll()")
    @CsvSource({
            "1, 2, asc",
            "2, 1, desc"
    })
    void findAllTest(int page, int size, String sort) {

        List<Brand> brandList = new ArrayList<>();
        brandList.add(new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."));
        brandList.add(new Brand(2L, "iPhone", "Línea de teléfonos inteligentes de alta gama."));
        brandList.add(new Brand(3L, "Sony", "Electrónica de consumo."));
        brandList.add(new Brand(
                4L,
                "Dewalt",
                "herramientas motorizadas para las industrias de la construcción y la carpintería."));

        Page<Brand> brands = new PageImpl<>(brandList);

        when(brandPersistencePort.findAll(any(Pageable.class))).thenReturn(brands);

        findAllBrandsUseCase.findAll(page, size, sort);

        verify(brandPersistencePort, times(1)).findAll(any(Pageable.class));
    }

    @ParameterizedTest
    @DisplayName("Should throw InvalidPaginationParametersException when page or size are wrong")
    @CsvSource({
            "-1, 2, asc",
            "1, -2, asc"
    })
    void findAll_shouldThrowInvalidPaginationParametersException(int page, int size, String sort) {
        assertThrows(InvalidPaginationParametersException.class, () -> findAllBrandsUseCase.findAll(page, size, sort));
    }
}
