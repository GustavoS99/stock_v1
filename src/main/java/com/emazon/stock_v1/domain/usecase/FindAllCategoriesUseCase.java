package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.helpers.GlobalConstants;
import com.emazon.stock_v1.domain.api.IFindAllCategoriesServicePort;
import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FindAllCategoriesUseCase implements IFindAllCategoriesServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public FindAllCategoriesUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Page<Category> findAll(int page, int size, String sortDirection) {
        if(page < GlobalConstants.START_PAGE || size <= GlobalConstants.START_PAGE_SIZE) {
            throw new InvalidPaginationParametersException();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.CATEGORY_SORT_BY).ascending());
        if(sortDirection.equalsIgnoreCase(GlobalConstants.DESCENDING_SORT))
            pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.CATEGORY_SORT_BY).descending());
        return categoryPersistencePort.findAll(pageable);
    }
}
