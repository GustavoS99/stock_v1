package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.mapper.CategoryRequestMapper;
import com.emazon.stock_v1.domain.api.ICategoryServicePort;
import com.emazon.stock_v1.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    private CategoryRequest categoryRequest;
    private Category category;

    @BeforeEach
    public void setUp() {
        categoryRequest = new CategoryRequest("Electrónica", "Artículos Electrónicos");
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
}
