package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final ISaveBrandServicePort saveBrandServicePort;
    private final BrandRequestMapper brandRequestMapper;

    @Override
    public void save(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.brandRequestToBrand(brandRequest);
        saveBrandServicePort.save(brand);
    }
}
