package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Category;

public interface ISaveCategoryServicePort {
    void save(Category category);
}
