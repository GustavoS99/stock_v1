package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.ItemResponse;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemResponseMapper {

    @Mapping(target = "categories.description", ignore = true)
    ItemResponse itemToItemResponse(Item item);
    
    PaginatedResult<ItemResponse> itemsToItemResponses(PaginatedResult<Item> items);
}
