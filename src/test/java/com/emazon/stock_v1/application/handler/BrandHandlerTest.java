package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.mapper.BrandRequestMapper;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandHandlerTest {

    @Mock
    private ISaveBrandServicePort saveBrandServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

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

}
