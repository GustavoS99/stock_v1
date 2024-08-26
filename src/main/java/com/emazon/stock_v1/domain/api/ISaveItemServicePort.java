package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Item;

public interface ISaveItemServicePort {
    void save(Item item);
}
