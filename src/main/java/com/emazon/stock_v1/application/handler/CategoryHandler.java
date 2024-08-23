package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.application.mapper.CategoryRequestMapper;
import com.emazon.stock_v1.application.mapper.CategoryResponseMapper;
import com.emazon.stock_v1.domain.api.IFindAllCategoriesServicePort;
import com.emazon.stock_v1.domain.api.ISaveCategoryServicePort;
import com.emazon.stock_v1.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler{

    private final ISaveCategoryServicePort saveCategoryServicePort;
    private final IFindAllCategoriesServicePort findAllCategoriesServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public void save(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.categoryRequestToCategory(categoryRequest);
        saveCategoryServicePort.save(category);
    }

    @Override
    public Page<CategoryResponse> findAll(int page, int size, String sortDirection) {
        Page<Category> categories = findAllCategoriesServicePort.findAll(page, size, sortDirection);
        return categories.map(categoryResponseMapper::categoryToCategoryResponse);
    }
}
