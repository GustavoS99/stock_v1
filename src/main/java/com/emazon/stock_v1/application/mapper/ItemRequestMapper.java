package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryRequestMapper.class})
public interface ItemRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand.id", ignore = true)
    Item itemRequestToItem(ItemRequest itemRequest);
}
