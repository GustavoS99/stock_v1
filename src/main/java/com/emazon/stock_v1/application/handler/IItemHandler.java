package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.dto.ItemResponse;
import com.emazon.stock_v1.domain.model.PaginatedResult;

public interface IItemHandler {

    void save(ItemRequest itemRequest);

    PaginatedResult<ItemResponse> findAll(int page, int size, String sortProperty, String sortDirection);

    PaginatedResult<ItemResponse> findByCategory(
            Long category, int page, int size, String sortBy, String sortDirection);

    PaginatedResult<ItemResponse> findByBrandName(
            String brandName, int page, int size, String sortBy, String sortDirection);
}
