package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.application.mapper.CategoryRequestMapper;
import com.emazon.stock_v1.application.mapper.CategoryResponseMapper;
import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(1L, "Electrónica", "Artículos Electrónicos");
        categoryRequest = new CategoryRequest(category.getName(), category.getDescription());
        categoryResponse = new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    @Test
    void when_saveCategory_expect_callToServicePort() {

        when(categoryRequestMapper.categoryRequestToCategory(any(CategoryRequest.class))).thenReturn(category);

        doNothing().when(categoryServicePort).save(any(Category.class));

        categoryHandler.save(categoryRequest);

        verify(categoryServicePort, times(1)).save(category);
    }

    @Test
    void when_findAllCategories_expect_callToServicePort() {
        int page = 0, size = 10;
        String sort = "desc";
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        PaginatedResult<Category> categories = new PaginatedResult<>(categoryList, page, size, categoryList.size());

        List<CategoryResponse> expectedCategoriesList = new ArrayList<>();
        expectedCategoriesList.add(categoryResponse);

        PaginatedResult<CategoryResponse> categoryResponses =
                new PaginatedResult<>(expectedCategoriesList, page, size, categoryList.size());

        when(categoryServicePort.findAll(any(PaginationRequest.class), anyString())).thenReturn(categories);

        when(categoryResponseMapper.categoriesToCategoryResponsePage(any())).thenReturn(categoryResponses);

        PaginatedResult<CategoryResponse> result = categoryHandler.findAll(page, size, sort);

        assertEquals(expectedCategoriesList.get(0).getName(), result.getItems().get(0).getName());
        assertEquals(expectedCategoriesList.get(0).getDescription(), result.getItems().get(0).getDescription());
        verify(categoryServicePort, times(1)).findAll(
                any(PaginationRequest.class), anyString());
    }
}
