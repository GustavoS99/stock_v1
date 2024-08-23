package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandRequestMapper {
    @Mapping(target = "id", ignore = true)
    Brand brandRequestToBrand(BrandRequest brandRequest);
}
