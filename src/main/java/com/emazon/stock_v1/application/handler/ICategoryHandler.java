package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.domain.model.PaginatedResult;

public interface ICategoryHandler {

    void save(CategoryRequest categoryRequest);

    PaginatedResult<CategoryResponse> findAll(int page, int size, String sortDirection);
}
