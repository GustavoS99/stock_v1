package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.ISaveItemServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.helpers.GlobalConstants;

import java.util.HashSet;
import java.util.Set;

public class SaveItemUseCase implements ISaveItemServicePort {

    private final IItemPersistencePort itemPersistencePort;

    public SaveItemUseCase(IItemPersistencePort itemPersistencePort) {
        this.itemPersistencePort = itemPersistencePort;
    }

    @Override
    public void save(Item item) {
        validateItemInput(item);

        validateBrand(item);

        validateCategories(item);

        itemPersistencePort.save(item);
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
            throw new InvalidNumOfCategories();

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
