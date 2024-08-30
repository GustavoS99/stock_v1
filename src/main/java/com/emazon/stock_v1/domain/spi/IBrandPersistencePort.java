package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandPersistencePort {
    Brand save(Brand brand);

    List<Brand> findAll(String sortDirection);

    Optional<Brand> findByName(String name);
}
