package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.constants.GlobalConstants;
import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(Category category) {
        categoryPersistencePort.save(category);
    }

    @Override
    public Page<Category> findAll(int page, int size, String sortDirection) {
        if(page < GlobalConstants.START_PAGE || size <= GlobalConstants.START_PAGE_SIZE) {
            throw new InvalidPaginationParametersException();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.SORT_BY).ascending());
        if(sortDirection.equalsIgnoreCase(GlobalConstants.DESCENDING_SORT))
            pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.SORT_BY).descending());
        return categoryPersistencePort.findAll(pageable);
    }
}
