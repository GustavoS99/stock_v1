package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.mapper.CategoryRequestMapper;
import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler{

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;

    @Override
    public void save(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.categoryRequestToCategory(categoryRequest);
        categoryServicePort.save(category);
    }

}
