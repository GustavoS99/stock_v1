package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {

    Category save(Category category);

    List<Category> findAll(String sortDirection);

    Optional<Category> findByName(String name);
}
