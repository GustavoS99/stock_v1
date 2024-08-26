package com.emazon.stock_v1.infraestructure.out.jpa.mapper;

import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class})
public interface ItemEntityMapper {
    ItemEntity itemToItemEntity(Item item);

    Item itemEntityToItem(ItemEntity itemEntity);
}
