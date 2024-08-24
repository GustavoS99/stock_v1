package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.helpers.GlobalConstants;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.exception.BrandsNotFoundException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @DisplayName("Should throw BrandAlreadyExistsException when brand exists")
    @Test
    void save_shouldThrowBrandAlreadyExistsException_whenBrandExists() {
        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");
        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        assertThrows(BrandAlreadyExistsException.class, () -> brandJpaAdapter.save(brand));
    }

    @DisplayName("Should assert equals content of the expected Page<brand> against the result. Plus verify the call to " +
            "brandRepository.findAll(Pageable.class) and brandEntityMapper.brandEntityToBrand(BrandEntity.class)")
    @Test
    void findAllTest() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).ascending());

        List<BrandEntity> brandEntityList = new ArrayList<>();
        brandEntityList.add(
                new BrandEntity(1L, "Asus", "Hardware de informática y electrónica de consumo."));

        Page<BrandEntity> brandEntities = new PageImpl<>(brandEntityList, pageable, brandEntityList.size());

        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");
        List<Brand> brandList = new ArrayList<>();
        brandList.add(brand);

        Page<Brand> brands = new PageImpl<>(brandList, pageable, brandList.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntities);

        when(brandEntityMapper.brandEntityToBrand(any(BrandEntity.class))).thenReturn(brand);

        Page<Brand> result = brandJpaAdapter.findAll(pageable);

        assertEquals(brands.getContent(), result.getContent());

        verify(brandRepository, times(1)).findAll(pageable);
        verify(brandEntityMapper, times(1)).brandEntityToBrand(any(BrandEntity.class));
    }

    @DisplayName("Should throw BrandsNotFoundException when brands not found")
    @Test
    void findAll_shouldThrowBrandsNotFoundException_whenBrandsNotFound() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).ascending());
        List<BrandEntity> brandEntityList = new ArrayList<>();
        Page<BrandEntity> brandEntities = new PageImpl<>(brandEntityList, pageable, brandEntityList.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntities);

        assertThrows(BrandsNotFoundException.class, () -> brandJpaAdapter.findAll(pageable));
    }
}
