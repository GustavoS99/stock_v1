package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.application.mapper.BrandResponseMapper;
import com.emazon.stock_v1.domain.api.IBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandHandlerTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    private Brand brand;
    private BrandRequest brandRequest;
    private BrandResponse brandResponse;

    @BeforeEach
    void setUp() {

        brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        brandRequest = new BrandRequest(brand.getName(), brand.getDescription());

        brandResponse = new BrandResponse(brand.getName(), brand.getDescription());
    }

    @Test
    void when_saveBrand_expect_callToServicePort() {

        when(brandRequestMapper.brandRequestToBrand(any(BrandRequest.class))).thenReturn(brand);

        doNothing().when(brandServicePort).save(any(Brand.class));

        brandHandler.save(brandRequest);

        verify(brandServicePort, times(1)).save(brand);
    }

    @Test
    void when_findAllBrands_expect_callToServicePort() {
        int page = 0, size = 10;
        String sort = "desc";

        List<Brand> brandList = new ArrayList<>();
        brandList.add(brand);

        PaginatedResult<Brand> brands = new PaginatedResult<>(brandList, page, size, brandList.size());

        List<BrandResponse> expectedBrandList = new ArrayList<>();
        expectedBrandList.add(brandResponse);

        PaginatedResult<BrandResponse> brandResponses = new PaginatedResult<>(
                expectedBrandList, page, size, expectedBrandList.size());

        when(brandServicePort.findAll(any(PaginationRequest.class), anyString())).thenReturn(brands);

        when(brandResponseMapper.brandsToBrandResponses(any())).thenReturn(brandResponses);

        PaginatedResult<BrandResponse> result = brandHandler.findAll(page, size, sort);

        assertEquals(expectedBrandList.get(0).getName(), result.getItems().get(0).getName());
        assertEquals(expectedBrandList.get(0).getDescription(), result.getItems().get(0).getDescription());
        verify(brandServicePort, times(1)).findAll(any(PaginationRequest.class), anyString());
    }
}
