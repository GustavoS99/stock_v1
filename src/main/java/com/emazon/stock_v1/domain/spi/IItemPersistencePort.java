package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface IItemPersistencePort {
    Item save(Item item);

    List<Item> findAll();

    List<Item> findAll(String sortProperty, String sortDirection);

    List<Item> findByCategoryId(Long categoryId);

    List<Item> findByCategoryId(Long categoryId, String sortProperty, String sortDirection);

    List<Item> findByBrandName(String brandName);

    List<Item> findByBrandName(String brandName, String sortBy, String sortDirection);

    Optional<Item> findByName(String name);
}
