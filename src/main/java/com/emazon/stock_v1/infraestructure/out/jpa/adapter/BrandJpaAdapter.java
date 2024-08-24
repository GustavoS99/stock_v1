package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.exception.BrandsNotFoundException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        Page<Brand> brands = brandRepository.findAll(pageable)
                .map(brandEntityMapper::brandEntityToBrand);
        if(brands.isEmpty())
            throw new BrandsNotFoundException();
        return brands;
    }
}
