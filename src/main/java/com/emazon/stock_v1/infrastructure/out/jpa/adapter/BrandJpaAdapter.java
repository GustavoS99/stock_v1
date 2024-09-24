package com.emazon.stock_v1.infrastructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.utils.GlobalConstants;
import com.emazon.stock_v1.utils.Helpers;
import com.emazon.stock_v1.infrastructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infrastructure.out.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public Brand save(Brand brand) {
        return brandEntityMapper.brandEntityToBrand(brandRepository.save(
                brandEntityMapper.brandToBrandEntity(brand)));
    }

    @Override
    public List<Brand> findAll(String sortDirection) {
        List<Brand> brands;

        if(sortDirection == null || sortDirection.isEmpty()) {
            brands = brandEntityMapper.brandEntitiesToBrands(brandRepository.findAll());
        } else {
            brands = brandEntityMapper.brandEntitiesToBrands(brandRepository.findAll(
                    Helpers.buildSortForOneProperty(GlobalConstants.BRAND_SORT_BY, sortDirection)));
        }

        return brands;
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name).map(brandEntityMapper::brandEntityToBrand);
    }
}
