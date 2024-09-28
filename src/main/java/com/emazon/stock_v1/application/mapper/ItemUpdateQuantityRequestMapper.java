package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.ItemUpdateQuantityRequest;
import com.emazon.stock_v1.domain.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemUpdateQuantityRequestMapper {

    Item toItem(ItemUpdateQuantityRequest itemUpdateQuantityRequest);
}
