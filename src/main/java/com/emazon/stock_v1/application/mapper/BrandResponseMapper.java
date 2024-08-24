package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper {

    BrandResponseMapper INSTANCE = Mappers.getMapper(BrandResponseMapper.class);

    BrandResponse brandToBrandResponse(Brand brand);
}
