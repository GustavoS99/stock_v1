package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.infraestructure.exception.*;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;

import java.util.HashSet;
import java.util.Set;

public class ItemJpaAdapter implements IItemPersistencePort {

    private final IItemRepository itemRepository;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;
    private final ItemEntityMapper itemEntityMapper;
    private final BrandEntityMapper brandEntityMapper;
    private final CategoryEntityMapper categoryEntityMapper;

    public ItemJpaAdapter(IItemRepository itemRepository, IBrandRepository brandRepository,
                          ICategoryRepository categoryRepository, ItemEntityMapper itemEntityMapper,
                          BrandEntityMapper brandEntityMapper, CategoryEntityMapper categoryEntityMapper) {
        this.itemRepository = itemRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.itemEntityMapper = itemEntityMapper;
        this.brandEntityMapper = brandEntityMapper;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public Item save(Item item) {
        if(itemRepository.findByName(item.getName()).isPresent())
            throw new ItemAlreadyExistsException();

        getBrandId(item);

        getCategoriesIds(item);

        return itemEntityMapper.itemEntityToItem(
                itemRepository.save(itemEntityMapper.itemToItemEntity(item)));
    }

    private void getBrandId(Item item) {
        item.setBrand(brandEntityMapper.brandEntityToBrand(brandRepository.findByName(
                item.getBrand().getName()).orElseThrow(BrandNotFoundException::new)));
    }

    private void getCategoriesIds(Item item) {
        Set<Category> categories = new HashSet<>();
        for(Category category : item.getCategories()){
            category = categoryEntityMapper.categoryEntityToCategory(
                    categoryRepository.findByName(
                            category.getName()).orElseThrow(CategoryNotFoundException::new));
            categories.add(category);
        }

        item.setCategories(categories);
    }
}
