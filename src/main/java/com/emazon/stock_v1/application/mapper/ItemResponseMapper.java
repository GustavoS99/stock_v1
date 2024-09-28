package com.emazon.stock_v1.application.mapper;

import com.emazon.stock_v1.application.dto.ItemResponse;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BrandResponseMapper.class, CategoryResponseMapper.class})
public interface ItemResponseMapper {

    ItemResponse itemToItemResponse(Item item);
    
    PaginatedResult<ItemResponse> itemsToItemResponses(PaginatedResult<Item> items);
}
