package com.emazon.stock_v1.infraestructure.input.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveCategoryTest() throws Exception {
        String categoryJson = """
                {
                "name": "Electrónica",
                "description": "Artículos electrónicos"
                }
                """;

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
                """,
                """
                {
                "name": "name",
                "description":""
                }
                """,
                        """
                {
                "name": "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
                "description":"description"
                }
                """,
                        """
                {
                "name": "Name",
                "description":
                "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"
                }
                """,
                "{}"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("providedCategoryRequestsForSaveCategoryException")
    void saveCategory_shouldReturnBadRequest_whenValidationFails(String categoryJson) throws Exception{

        mockMvc.perform(post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest());
    }
}
