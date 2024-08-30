package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.ItemRequest;

public interface IItemHandler {

    void save(ItemRequest itemRequest);

}
