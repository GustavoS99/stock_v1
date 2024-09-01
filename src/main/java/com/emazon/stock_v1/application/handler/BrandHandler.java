package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.application.mapper.BrandResponseMapper;
import com.emazon.stock_v1.domain.api.IBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void save(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.brandRequestToBrand(brandRequest);
        brandServicePort.save(brand);
    }

    @Override
    public PaginatedResult<BrandResponse> findAll(int page, int size, String sortDirection) {
        return brandResponseMapper.brandsToBrandResponses(
                brandServicePort.findAll(new PaginationRequest(page, size), sortDirection));
    }
}
