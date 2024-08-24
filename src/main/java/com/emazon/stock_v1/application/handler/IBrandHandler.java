package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import org.springframework.data.domain.Page;

public interface IBrandHandler {
    void save(BrandRequest brandRequest);

    Page<BrandResponse> findAll(int page, int size, String sortDirection);
}
