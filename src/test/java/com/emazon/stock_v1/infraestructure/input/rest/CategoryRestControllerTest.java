package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.handler.ICategoryHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {

    @SpyBean
    private ICategoryHandler categoryHandler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should verify the expected 201 status")
    void saveCategoryTest() throws Exception {
        String categoryJson = """
                {
                "name": "Electrónica",
                "description": "Artículos electrónicos"
                }
                """;

        doNothing().when(categoryHandler).save(any(CategoryRequest.class));

        mockMvc.perform(post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isCreated());
    }

    private static Stream<Arguments> providedCategoryRequestsForSaveCategoryException(){
        return Stream.of(
                Arguments.of(
                        """
                {
                "name": "",
                "description":"description"
                }
                """
                ),
                Arguments.of("""
                {
                "name": "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
                "description":"description"
                }
                """),
                Arguments.of("""
                {
                "name": "Name",
                "description":
                "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"
                }
                """),
                Arguments.of("{}")
        );
    }


    @DisplayName("Should verify the expected 400 status")
    @ParameterizedTest
    @MethodSource("providedCategoryRequestsForSaveCategoryException")
    void saveCategory_shouldReturnBadRequest_whenValidationFails(String categoryJson) throws Exception{

        doCallRealMethod().when(categoryHandler).save(any(CategoryRequest.class));

        mockMvc.perform(post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Should verify the expected 200 status")
    @Test
    void findAllTest() throws Exception{

        mockMvc.perform(get("/categories/"))
                .andExpect(status().isOk());
    }
}
