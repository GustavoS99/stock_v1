package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.EmptyCategoryDescriptionException;
import com.emazon.stock_v1.helpers.GlobalConstants;
import com.emazon.stock_v1.domain.api.ISaveCategoryServicePort;
import com.emazon.stock_v1.domain.exception.EmptyCategoryNameException;
import com.emazon.stock_v1.domain.exception.InvalidLengthCategoryDescriptionException;
import com.emazon.stock_v1.domain.exception.InvalidLengthCategoryNameException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;

public class SaveCategoryUseCase implements ISaveCategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public SaveCategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(Category category) {
        validateNameInput(category);

        validateDescriptionInput(category);

        categoryPersistencePort.save(category);
    }
    
    private void validateDescriptionInput(Category category) {
        if(category.getDescription() == null || category.getDescription().isEmpty())
            throw new EmptyCategoryDescriptionException();

        category.setDescription(category.getDescription().trim());

        if (category.getDescription().isEmpty()
                || category.getDescription().length() > GlobalConstants.LENGTH_CATEGORY_DESCRIPTION)
            throw new InvalidLengthCategoryDescriptionException();
    }

    private void validateNameInput(Category category) {
        if(category.getName() == null || category.getName().isEmpty())
            throw new EmptyCategoryNameException();

        category.setName(category.getName().trim());

        if(category.getName().length() > GlobalConstants.LENGTH_CATEGORY_NAME)
            throw new InvalidLengthCategoryNameException();
    }
}
