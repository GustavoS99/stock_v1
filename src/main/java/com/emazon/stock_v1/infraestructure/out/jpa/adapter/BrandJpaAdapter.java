package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public Brand save(Brand brand) {
        if(brandRepository.findByName(brand.getName()).isPresent())
            throw new BrandAlreadyExistsException();

        BrandEntity brandEntity = brandEntityMapper.brandToBrandEntity(brand);
        BrandEntity result = brandRepository.save(brandEntity);
        return brandEntityMapper.brandEntityToBrand(result);
    }
}
