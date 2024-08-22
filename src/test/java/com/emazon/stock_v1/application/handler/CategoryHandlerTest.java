package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.CategoryResponse;
import com.emazon.stock_v1.application.mapper.CategoryRequestMapper;
import com.emazon.stock_v1.application.mapper.CategoryResponseMapper;
import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
        categoryRequest = new CategoryRequest("Electrónica", "Artículos Electrónicos");
        categoryResponse = new CategoryResponse("Electrónica", "Artículos Electrónicos");
        category = new Category(1L, "Electrónica", "Artículos Electrónicos");
    }

    @Test
    void saveTest() {
        when(categoryRequestMapper.categoryRequestToCategory(categoryRequest)).thenReturn(category);
        doNothing().when(categoryServicePort).save(category);

        categoryHandler.save(categoryRequest);

        assertEquals(categoryRequest.getName(), category.getName());
        assertEquals(categoryRequest.getDescription(), category.getDescription());
        verify(categoryServicePort, times(1)).save(category);
    }

    @Test
    void findAllTest(){
        int page = 0, size = 10;
        String sort = "desc";
        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(category);

        Page<Category> categories = new PageImpl<>(categoriesList);

        List<CategoryResponse> expectedCategoriesList = new ArrayList<>();
        expectedCategoriesList.add(new CategoryResponse("Electrónica", "Artículos Electrónicos"));

        when(categoryServicePort.findAll(anyInt(), anyInt(), anyString())).thenReturn(categories);

        when(categoryResponseMapper.categoryToCategoryResponse(any(Category.class))).thenReturn(categoryResponse);

        Page<CategoryResponse> result = categoryHandler.findAll(page, size, sort);

        assertEquals(expectedCategoriesList, result.getContent());
        verify(categoryServicePort, times(1)).findAll(anyInt(), anyInt(), anyString());
    }
}
