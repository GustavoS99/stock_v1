package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    static BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @DisplayName("Should validate the returned value of BrandJpaAdapter.save(Brand.class)")
    @Test
    void saveTest(){
        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");

        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

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
        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");
        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        assertThrows(BrandAlreadyExistsException.class, () -> brandJpaAdapter.save(brand));
    }
}
