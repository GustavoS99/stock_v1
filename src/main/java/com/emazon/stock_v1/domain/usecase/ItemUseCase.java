package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.IItemServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.utils.GlobalConstants;
import com.emazon.stock_v1.domain.exception.BrandNotFoundException;
import com.emazon.stock_v1.domain.exception.CategoryNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class ItemUseCase implements IItemServicePort {

    private final IItemPersistencePort itemPersistencePort;

    private final ICategoryPersistencePort categoryPersistencePort;

    private final IBrandPersistencePort brandPersistencePort;

    public ItemUseCase(IItemPersistencePort itemPersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.itemPersistencePort = itemPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void save(Item item) {
        validateItemInput(item);

        validateBrand(item);

        validateCategories(item);

        item.getBrand().setId(getBrandId(item.getBrand()));

        for(Category category : item.getCategories()){
            category.setId(getCategoryId(category));
        }

        itemPersistencePort.save(item);
    }

    private long getBrandId(Brand brand) {
        return brandPersistencePort.findByName(brand.getName()).orElseThrow(this::brandNotFoundException).getId();
    }

    private BrandNotFoundException brandNotFoundException() {
        return new BrandNotFoundException();
    }

    private long getCategoryId(Category category) {
        return categoryPersistencePort.findByName(
                category.getName()).orElseThrow(this::categoryNotFoundException).getId();
    }

    private CategoryNotFoundException categoryNotFoundException() {
        return new CategoryNotFoundException();
    }

    private void validateBrand(Item item) {
        if (item.getBrand() == null)
            throw new EmptyBrandOfItemException();

        if (item.getBrand().getName() == null || item.getBrand().getName().trim().isEmpty())
            throw new EmptyBrandNameException();
    }

    private void validateItemInput(Item item) {
        validateNameInput(item);

        validateDescriptionInput(item);
    }

    private void validateNameInput(Item item) {
        if(item.getName() == null || item.getName().trim().isEmpty())
            throw new EmptyItemNameException();

        item.setName(item.getName().trim());

        if(item.getName().length() > GlobalConstants.LENGTH_ITEM_NAME)
            throw new InvalidLengthItemNameException();
    }

    private void validateDescriptionInput(Item item) {
        if(item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            throw new EmptyItemDescriptionException();
        }

        item.setDescription(item.getDescription().trim());

        if(item.getDescription().length() > GlobalConstants.LENGTH_ITEM_DESCRIPTION)
            throw new InvalidLengthItemDescriptionException();
    }

    private void validateCategories(Item item) {

        validateNumOfCategories(item);

        hasDuplicateCategories(item);
    }

    private void validateNumOfCategories(Item item) {

        if(item.getCategories() == null || item.getCategories().size() < GlobalConstants.ITEM_MIN_CATEGORIES
                || item.getCategories().size() > GlobalConstants.ITEM_MAX_CATEGORIES)
            throw new InvalidNumOfCategoriesException();

        for(Category category : item.getCategories())
            if(category == null || category.getName() == null || category.getName().trim().isEmpty())
                throw new EmptyCategoryNameException();
    }

    private void hasDuplicateCategories(Item item) {
        Set<String> categoryNames = new HashSet<>();
        for (Category category : item.getCategories())
            if(!categoryNames.add(category.getName()))
                throw new ItemHasDuplicateCategories();

    }
}
