package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {

        brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");
    }

    @Test
    void when_saveBrand_expect_callToPersistence() {

        when(brandPersistencePort.save(brand)).thenReturn(brand);

        brandUseCase.save(brand);

        verify(brandPersistencePort, times(1)).save(brand);
    }

    @Test
    void expect_InvalidLengthBrandNameException_when_nameIsLong() {
        brand.setName("AsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsus");

        assertThrows(InvalidLengthBrandNameException.class, () -> brandUseCase.save(brand));
    }

    @Test
    void expect_InvalidLengthBrandDescriptionException_when_descriptionIsLong() {
        brand.setDescription("DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription");

        assertThrows(InvalidLengthBrandDescriptionException.class, () -> brandUseCase.save(brand));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''",
            "' '",
            "null"
    }, nullValues = {"null"})
    void expect_EmptyBrandNameException_when_nameIsEmpty(String name) {
        brand.setName(name);

        assertThrows(EmptyBrandNameException.class, () -> brandUseCase.save(brand));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''",
            "' '",
            "null"
    }, nullValues = {"null"})
    void expect_EmptyBrandDescriptionException_when_descriptionIsEmpty(String description) {
        brand.setDescription(description);

        assertThrows(EmptyBrandDescriptionException.class, () -> brandUseCase.save(brand));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 2, desc",
            "0, 1, asc",
            "0, 2, ''",
            "0, 2, null"
    }, nullValues = {"null"})
    void when_findAll_expect_callToPersistence(int page, int size, String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Brand> brands = new ArrayList<>();
        brands.add(brand);

        if(sortDirection == null) {
            when(brandPersistencePort.findAll(null)).thenReturn(brands);
        } else {
            when(brandPersistencePort.findAll(anyString())).thenReturn(brands);
        }

        brandUseCase.findAll(paginationRequest, sortDirection);

        verify(brandPersistencePort, times(1)).findAll(sortDirection);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, asc",
            "1, -2, desc",
    })
    void expect_InvalidPaginationParametersException_when_pageOrSizeAreBad(int page, int size, String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        assertThrows(InvalidPaginationParametersException.class, () ->
                brandUseCase.findAll(paginationRequest, sortDirection));
    }
}
