package com.emazon.stock_v1.domain.api;

import com.emazon.stock_v1.domain.model.Brand;

public interface ISaveBrandServicePort {
    void save(Brand brand);
}
