package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.mapper.ItemRequestMapper;
import com.emazon.stock_v1.domain.api.ISaveItemServicePort;
import com.emazon.stock_v1.domain.model.Item;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemHandler implements IItemHandler {
    private final ISaveItemServicePort saveItemServicePort;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public void save(ItemRequest itemRequest) {
        Item item = itemRequestMapper.itemRequestToItem(itemRequest);
        saveItemServicePort.save(item);
    }
}
