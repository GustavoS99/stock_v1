package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.*;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;
import com.emazon.stock_v1.utils.GlobalConstants;

import java.util.List;

import static com.emazon.stock_v1.utils.Helpers.buildSortForOneProperty;
import static com.emazon.stock_v1.utils.Helpers.buildSortForTwoProperties;

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

    @Override
    public List<Item> findAll() {
        return itemEntityMapper.itemEntitiesToItems(itemRepository.findAll());
    }

    @Override
    public List<Item> findAll(String sortProperty, String sortDirection) {
        List<Item> items;

        if (sortProperty.equals(GlobalConstants.ITEMS_SORT_BY_CATEGORY)) {
            items = itemEntityMapper.itemEntitiesToItems(itemRepository.findAll(
                    buildSortForTwoProperties(GlobalConstants.ITEM_SORT_BY, sortProperty, sortDirection)));
        } else {
            items = itemEntityMapper.itemEntitiesToItems(
                    itemRepository.findAll(buildSortForOneProperty(sortProperty, sortDirection)));
        }

        return items;
    }

    @Override
    public List<Item> findByCategoryId(Long categoryId) {
        return itemEntityMapper.itemEntitiesToItems(itemRepository.findByCategoriesId(categoryId));
    }

    @Override
    public List<Item> findByCategoryId(Long categoryId, String sortProperty, String sortDirection) {
        List<Item> items;

        if (sortProperty.equals(GlobalConstants.ITEMS_SORT_BY_CATEGORY)) {
            items = itemEntityMapper.itemEntitiesToItems(itemRepository.findByCategoriesId(categoryId,
                    buildSortForTwoProperties(GlobalConstants.ITEM_SORT_BY, sortProperty, sortDirection)));
        } else {
            items = itemEntityMapper.itemEntitiesToItems(itemRepository.findByCategoriesId(
                    categoryId, buildSortForOneProperty(sortProperty, sortDirection)));
        }

        return items;
    }

    @Override
    public List<Item> findByBrandName(String brandName) {
        return itemEntityMapper.itemEntitiesToItems(itemRepository.findByBrandName(brandName));
    }

    @Override
    public List<Item> findByBrandName(String brandName, String sortBy, String sortDirection) {
        List<Item> items;

        if (sortBy.equals(GlobalConstants.ITEMS_SORT_BY_CATEGORY)) {
            items = itemEntityMapper.itemEntitiesToItems(itemRepository.findByBrandName(brandName,
                    buildSortForTwoProperties(GlobalConstants.ITEM_SORT_BY, sortBy, sortDirection)));
        } else {
            items = itemEntityMapper.itemEntitiesToItems(itemRepository.findByBrandName(
                    brandName, buildSortForOneProperty(sortBy, sortDirection)));
        }

        return items;
    }
}
