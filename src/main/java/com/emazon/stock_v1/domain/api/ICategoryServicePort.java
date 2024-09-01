package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;

public interface ICategoryServicePort {

    void save(Category category);

    PaginatedResult<Category> findAll(PaginationRequest paginationRequest, String sortDirection);
}
