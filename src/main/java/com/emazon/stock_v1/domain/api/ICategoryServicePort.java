package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Category;
import org.springframework.data.domain.Page;

public interface ICategoryServicePort {

    void save(Category category);

    Page<Category> findAll(int page, int size, String sortDirection);
}
