package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface ICategoryHandler {

    void save(CategoryRequest categoryRequest);

    Page<CategoryResponse> findAll(int page, int size, String sortDirection);
}
