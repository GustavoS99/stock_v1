package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.domain.model.PaginatedResult;

public interface IBrandHandler {
    void save(BrandRequest brandRequest);

    PaginatedResult<BrandResponse> findAll(int page, int size, String sortDirection);
}
