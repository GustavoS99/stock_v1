package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBrandPersistencePort {
    Brand save(Brand brand);

    Page<Brand> findAll(Pageable pageable);
}
