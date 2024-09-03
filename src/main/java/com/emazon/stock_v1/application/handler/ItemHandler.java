package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.dto.ItemResponse;
import com.emazon.stock_v1.application.mapper.ItemRequestMapper;
import com.emazon.stock_v1.application.mapper.ItemResponseMapper;
import com.emazon.stock_v1.domain.api.IItemServicePort;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemHandler implements IItemHandler {
    private final IItemServicePort itemServicePort;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemResponseMapper itemResponseMapper;

    @Override
    public void save(ItemRequest itemRequest) {
        Item item = itemRequestMapper.itemRequestToItem(itemRequest);
        itemServicePort.save(item);
    }

    @Override
    public PaginatedResult<ItemResponse> findAll(int page, int size, String sortProperty, String sortDirection) {
        return itemResponseMapper.itemsToItemResponses(
                itemServicePort.findAll(new PaginationRequest(page, size), sortProperty, sortDirection)
        );
    }

    @Override
    public PaginatedResult<ItemResponse> findByCategory(
            Long category, int page, int size, String sortBy, String sortDirection
    ) {
        return itemResponseMapper.itemsToItemResponses(
                itemServicePort.findByCategory(category, new PaginationRequest(page, size), sortBy, sortDirection)
        );
    }

    @Override
    public PaginatedResult<ItemResponse> findByBrandName(
            String brandName, int page, int size, String sortBy, String sortDirection) {
        return itemResponseMapper.itemsToItemResponses(
                itemServicePort.findByBrandName(brandName, new PaginationRequest(page, size), sortBy, sortDirection)
        );
    }
}
