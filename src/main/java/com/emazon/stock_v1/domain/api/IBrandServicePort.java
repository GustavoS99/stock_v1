package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;

public interface IBrandServicePort {

    void save(Brand brand);

    PaginatedResult<Brand> findAll(PaginationRequest paginationRequest, String sortDirection);

}
