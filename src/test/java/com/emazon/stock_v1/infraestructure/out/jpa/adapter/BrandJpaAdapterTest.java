package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.exception.BrandsNotFoundException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    static BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    private Brand brand;
    private BrandEntity brandEntity;

    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");
        brandEntity = new BrandEntity(brand.getId(), brand.getName(), brand.getDescription());
    }

    @Test
    void when_saveBrand_expect_fieldsSavedSuccessfully() {

        when(brandEntityMapper.brandToBrandEntity(any(Brand.class))).thenReturn(brandEntity);
        when(brandRepository.save(any(BrandEntity.class))).thenReturn(brandEntity);
        when(brandEntityMapper.brandEntityToBrand(any(BrandEntity.class))).thenReturn(brand);

        Brand savedBrand = brandJpaAdapter.save(brand);

        assertNotNull(savedBrand, "The result should not be null");
        assertEquals(brand.getId(), savedBrand.getId());
        assertEquals(brand.getName(), savedBrand.getName());
        assertEquals(brand.getDescription(), savedBrand.getDescription());
    }

    @Test
    void save_shouldThrowBrandAlreadyExistsException_whenBrandExists() {

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        assertThrows(BrandAlreadyExistsException.class, () -> brandJpaAdapter.save(brand));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "''",
            "desc",
            "asc"
    }, nullValues = {"null"})
    void when_findAll_expect_callToRepository(String sortDirection) {

        List<BrandEntity> brandEntities = new ArrayList<>();
        brandEntities.add(brandEntity);

        List<Brand> brands = new ArrayList<>();
        brands.add(brand);

        if(sortDirection == null || sortDirection.isEmpty()) {
            when(brandRepository.findAll()).thenReturn(brandEntities);
        } else {
            when(brandRepository.findAll(any(Sort.class))).thenReturn(brandEntities);
        }

        when(brandEntityMapper.brandEntitiesToBrands(anyList())).thenReturn(brands);

        List<Brand> result = brandJpaAdapter.findAll(sortDirection);

        assertEquals(brands, result);

        if(sortDirection == null || sortDirection.isEmpty()) {
            verify(brandRepository, times(1)).findAll();
        } else {
            verify(brandRepository, times(1)).findAll(any(Sort.class));
        }

    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "''",
            "desc",
            "asc"
    }, nullValues = {"null"})
    void findAll_shouldThrowBrandsNotFoundException_whenBrandsNotFound(String sortDirection) {

        List<BrandEntity> brandEntities = new ArrayList<>();

        List<Brand> brands = new ArrayList<>();

        if(sortDirection == null || sortDirection.isEmpty()) {
            when(brandRepository.findAll()).thenReturn(brandEntities);
        } else {
            when(brandRepository.findAll(any(Sort.class))).thenReturn(brandEntities);
        }

        when(brandEntityMapper.brandEntitiesToBrands(anyList())).thenReturn(brands);

        assertThrows(BrandsNotFoundException.class, () -> brandJpaAdapter.findAll(sortDirection));
    }
}
