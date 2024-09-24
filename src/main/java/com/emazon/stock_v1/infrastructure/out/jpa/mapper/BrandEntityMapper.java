package com.emazon.stock_v1.infrastructure.out.jpa.mapper;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntityMapper INSTANCE = Mappers.getMapper(BrandEntityMapper.class);

    BrandEntity brandToBrandEntity(Brand brand);

    Brand brandEntityToBrand(BrandEntity brandEntity);

    List<Brand> brandEntitiesToBrands(List<BrandEntity> brandEntities);
}
