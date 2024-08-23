package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Brand;

public interface IBrandPersistencePort {
    Brand save(Brand brand);
}
