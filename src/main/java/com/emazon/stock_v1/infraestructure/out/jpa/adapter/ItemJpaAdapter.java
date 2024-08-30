package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.*;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;

public class ItemJpaAdapter implements IItemPersistencePort {

    private final IItemRepository itemRepository;
    private final ItemEntityMapper itemEntityMapper;

    public ItemJpaAdapter(IItemRepository itemRepository, ItemEntityMapper itemEntityMapper) {
        this.itemRepository = itemRepository;
        this.itemEntityMapper = itemEntityMapper;
    }

    @Override
    public Item save(Item item) {
        if(itemRepository.findByName(item.getName()).isPresent())
            throw new ItemAlreadyExistsException();

        return itemEntityMapper.itemEntityToItem(
                itemRepository.save(itemEntityMapper.itemToItemEntity(item)));
    }
}
