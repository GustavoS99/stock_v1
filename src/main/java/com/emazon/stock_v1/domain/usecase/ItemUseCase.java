package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.IItemServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.*;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.domain.exception.ItemsNotFoundException;
import com.emazon.stock_v1.utils.GlobalConstants;
import com.emazon.stock_v1.domain.exception.BrandNotFoundException;
import com.emazon.stock_v1.domain.exception.CategoryNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.emazon.stock_v1.utils.Helpers.isOrdered;

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

    @Override
    public PaginatedResult<Item> findAll(
            PaginationRequest paginationRequest, String sortProperty, String sortDirection
    ) {
        validatePagination(paginationRequest);

        List<Item> items = (isOrdered(sortProperty, sortDirection))? validateIfListIsEmpty(itemPersistencePort
                .findAll(getSortProperty(sortProperty), sortDirection)) : validateIfListIsEmpty(
                        itemPersistencePort.findAll());

        int totalPages = (int) Math.ceil((double) items.size() / paginationRequest.getSize());

        List<Item> paginatedItems = getPaginated(
                items, validatePage(paginationRequest.getPage(), totalPages), paginationRequest.getSize());

        return new PaginatedResult<>(paginatedItems, paginationRequest.getPage(), totalPages, items.size());
    }

    @Override
    public PaginatedResult<Item> findByCategory(
            Long category, PaginationRequest paginationRequest, String sortBy, String sortDirection
    ) {
        validatePagination(paginationRequest);

        List<Item> items = (isOrdered(sortBy, sortDirection))? validateIfListIsEmpty(itemPersistencePort
                .findByCategoryId(category, getSortProperty(sortBy), sortDirection)) : validateIfListIsEmpty(
                        itemPersistencePort.findByCategoryId(category));

        int totalPages = (int) Math.ceil((double) items.size() / paginationRequest.getSize());

        List<Item> paginatedItems = getPaginated(
                items, validatePage(paginationRequest.getPage(), totalPages), paginationRequest.getSize());

        return new PaginatedResult<>(paginatedItems, paginationRequest.getPage(), totalPages, items.size());
    }

    @Override
    public PaginatedResult<Item> findByBrandName(
            String brandName, PaginationRequest paginationRequest, String sortBy, String sortDirection
    ) {
        validatePagination(paginationRequest);

        List<Item> items = (isOrdered(sortBy, sortDirection))? validateIfListIsEmpty(itemPersistencePort
                .findByBrandName(brandName, getSortProperty(sortBy), sortDirection)) : validateIfListIsEmpty(
                        itemPersistencePort.findByBrandName(brandName));

        int totalPages = (int) Math.ceil((double) items.size() / paginationRequest.getSize());

        List<Item> paginatedItems = getPaginated(items, validatePage(
                paginationRequest.getPage(), totalPages), paginationRequest.getSize());

        return new PaginatedResult<>(paginatedItems, paginationRequest.getPage(), totalPages, items.size());
    }

    private void validatePagination(PaginationRequest paginationRequest) {

        if(paginationRequest.getPage() < GlobalConstants.START_PAGE
                || paginationRequest.getSize() <= GlobalConstants.START_PAGE_SIZE) {
            throw new InvalidPaginationParametersException();
        }
    }

    private List<Item> validateIfListIsEmpty(List<Item> all) {
        if(all.isEmpty())
            throw new ItemsNotFoundException();

        return all;
    }

    private String getSortProperty(String sortProperty) {

        if (sortProperty.equalsIgnoreCase(GlobalConstants.CATEGORY)) {
            sortProperty = GlobalConstants.ITEMS_SORT_BY_CATEGORY;
        } else if (sortProperty.equalsIgnoreCase(GlobalConstants.BRAND)) {
            sortProperty = GlobalConstants.ITEMS_SORT_BY_BRAND;
        }

        return sortProperty;
    }

    private int validatePage(int page, int totalPages) {

        if(page >= totalPages)
            throw new PageExceedTotalPagesException();

        return page;
    }

    private List<Item> getPaginated(List<Item> items, int page, int size) {

        int start = page * size;
        int end = Math.min(start + size, items.size());

        return items.subList(start, end);
    }
}
