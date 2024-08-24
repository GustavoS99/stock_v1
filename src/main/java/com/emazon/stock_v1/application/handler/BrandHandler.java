package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.application.mapper.BrandResponseMapper;
import com.emazon.stock_v1.domain.api.IFindAllBrandsServicePort;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final ISaveBrandServicePort saveBrandServicePort;
    private final IFindAllBrandsServicePort findAllBrandsServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void save(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.brandRequestToBrand(brandRequest);
        saveBrandServicePort.save(brand);
    }

    @Override
    public Page<BrandResponse> findAll(int page, int size, String sortDirection) {
        Page<Brand> brands = findAllBrandsServicePort.findAll(page, size, sortDirection);
        return brands.map(brandResponseMapper::brandToBrandResponse);
    }
}
