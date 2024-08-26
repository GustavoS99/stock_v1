package com.emazon.stock_v1.domain.spi;

import com.emazon.stock_v1.domain.model.Item;

public interface IItemPersistencePort {
    Item save(Item item);
}
