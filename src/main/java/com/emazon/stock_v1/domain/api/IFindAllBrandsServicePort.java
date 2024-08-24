package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Brand;
import org.springframework.data.domain.Page;

public interface IFindAllBrandsServicePort {
    Page<Brand> findAll(int page, int size, String sortDirection);
}
