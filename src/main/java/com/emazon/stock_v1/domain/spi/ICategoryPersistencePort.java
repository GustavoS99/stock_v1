package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Category;

public interface ICategoryPersistencePort {

    Category save(Category category);
}
