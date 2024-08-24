package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.application.mapper.BrandResponseMapper;
import com.emazon.stock_v1.domain.api.IFindAllBrandsServicePort;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandHandlerTest {

    @Mock
    private ISaveBrandServicePort saveBrandServicePort;

    @Mock
    private IFindAllBrandsServicePort findAllBrandsServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    @DisplayName("Should assert Brand saved fields and verify the call to saveBrandServicePort.save()")
    @Test
    void saveTest() {
        BrandRequest brandRequest = new BrandRequest(
                "Asus", "Hardware de informática y electrónica de consumo.");
        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");
        when(brandRequestMapper.brandRequestToBrand(any(BrandRequest.class))).thenReturn(brand);
        doNothing().when(saveBrandServicePort).save(any(Brand.class));

        brandHandler.save(brandRequest);

        verify(saveBrandServicePort, times(1)).save(brand);
    }

    @DisplayName(
            "Should assert the result list of brands and verify the call to IFindAllBrandsServicePort.findAll()"
    )
    @Test
    void findAllTest() {
        int page = 0, size = 10;
        String sort = "desc";

        List<Brand> brandList = new ArrayList<>();
        brandList.add(new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."));

        Page<Brand> brands = new PageImpl<>(brandList);

        BrandResponse brandResponse = new BrandResponse(
                "Asus", "Hardware de informática y electrónica de consumo.");

        List<BrandResponse> expectedBrandList = new ArrayList<>();
        expectedBrandList.add(brandResponse);

        when(findAllBrandsServicePort.findAll(anyInt(), anyInt(), anyString())).thenReturn(brands);

        when(brandResponseMapper.brandToBrandResponse(any(Brand.class))).thenReturn(brandResponse);

        Page<BrandResponse> result = brandHandler.findAll(page, size, sort);

        assertEquals(expectedBrandList, result.getContent());
        verify(findAllBrandsServicePort, times(1)).findAll(page, size, sort);
    }
}
