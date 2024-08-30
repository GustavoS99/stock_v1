package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.BrandResponse;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper {

    BrandResponse brandToBrandResponse(Brand brand);

    PaginatedResult<BrandResponse> brandsToBrandResponses(PaginatedResult<Brand> brands);
}
