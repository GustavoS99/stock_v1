package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryPersistencePort {

    Category save(Category category);

    Page<Category> findAll(Pageable pageable);
}
