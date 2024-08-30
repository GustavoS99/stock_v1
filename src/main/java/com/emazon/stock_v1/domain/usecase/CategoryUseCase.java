package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.utils.GlobalConstants;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(Category category) {
        validateNameInput(category);

        validateDescriptionInput(category);

        categoryPersistencePort.save(category);
    }

    private void validateDescriptionInput(Category category) {
        if(category.getDescription() == null || category.getDescription().trim().isEmpty())
            throw new EmptyCategoryDescriptionException();

        category.setDescription(category.getDescription().trim());

        if (category.getDescription().length() > GlobalConstants.LENGTH_CATEGORY_DESCRIPTION)
            throw new InvalidLengthCategoryDescriptionException();
    }

    private void validateNameInput(Category category) {
        if(category.getName() == null || category.getName().trim().isEmpty())
            throw new EmptyCategoryNameException();

        category.setName(category.getName().trim());

        if(category.getName().length() > GlobalConstants.LENGTH_CATEGORY_NAME)
            throw new InvalidLengthCategoryNameException();
    }

    @Override
    public PaginatedResult<Category> findAll(PaginationRequest paginationRequest, String sortDirection) {
        if(paginationRequest.getPage() < GlobalConstants.START_PAGE
                || paginationRequest.getSize() <= GlobalConstants.START_PAGE_SIZE) {
            throw new InvalidPaginationParametersException();
        }
        List<Category> categories = categoryPersistencePort.findAll(sortDirection);

        List<Category> paginatedCategories = getPaginated(
                categories, paginationRequest.getPage(), paginationRequest.getSize());

        int totalPages = (int) Math.ceil((double) categories.size() / paginationRequest.getSize());

        return new PaginatedResult<>(paginatedCategories, paginationRequest.getPage(), totalPages, categories.size());
    }

    private List<Category> getPaginated(List<Category> categories, int page, int size) {

        int start = page * size;
        int end = Math.min(start + size, categories.size());

        return categories.subList(start, end);
    }
}
