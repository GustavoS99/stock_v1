package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;

public interface IItemServicePort {

    void save(Item item);

    PaginatedResult<Item> findAll(
            PaginationRequest paginationRequest, String sortProperty, String sortDirection);

    PaginatedResult<Item> findByCategory(
            Long category, PaginationRequest paginationRequest, String sortBy, String sortDirection);

    PaginatedResult<Item> findByBrandName(
            String brandName, PaginationRequest paginationRequest, String sortBy, String sortDirection);

    Item findByName(String name);
}
