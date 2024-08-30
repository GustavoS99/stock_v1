package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.handler.ICategoryHandler;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @MockBean
    private ICategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    private String categoryJson;

    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp(){
        categoryJson = """
                {
                "name": "Electrónica",
                "description": "Artículos electrónicos"
                }
                """;

        categoryEntity = new CategoryEntity(1L, "Electrónica", "Artículos electrónicos");
    }

    @Test
    void when_save_expect_createdStatus() throws Exception {

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        mockMvc.perform(post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isCreated());
    }

    private static Stream<Arguments> providedSave_shouldReturnBadRequest_whenValidationFails(){
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
                "name": null,
                "description":"description"
                }
                """),
                Arguments.of("""
                {
                "name": " ",
                "description":"description"
                }
                """),
                Arguments.of("""
                {
                "name": "Name",
                "description":""
                }
                """),
                Arguments.of("""
                {
                "name": "Name",
                "description": null
                }
                """),
                Arguments.of("""
                {
                "name": "Name",
                "description":" "
                }
                """),
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

    @ParameterizedTest
    @MethodSource("providedSave_shouldReturnBadRequest_whenValidationFails")
    void save_shouldReturnBadRequest_whenValidationFails(String categoryJson) throws Exception{

        doCallRealMethod().when(categoryHandler).save(any(CategoryRequest.class));

        mockMvc.perform(post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void expect_ConflictStatus_when_CategoryExists() throws Exception {

        Optional<CategoryEntity> optionalCategoryEntity = Optional.of(categoryEntity);

        when(categoryRepository.findByName(anyString())).thenReturn(optionalCategoryEntity);

        mockMvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isConflict());
    }

    @Test
    void when_findAll_expect_statusOk() throws Exception{

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(new CategoryEntity(
                1L, "Electrónica", "Artículos electrónicos"));

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        mockMvc.perform(get("/categories/"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/categories/?sortDirection=desc",
            "/categories/?sortDirection=asc"
    })
    void when_findAllOrdered_expect_statusOk(String url) throws Exception {

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(categoryEntity);

        when(categoryRepository.findAll(any(Sort.class))).thenReturn(categoryEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    void expect_statusNotFound_when_thereIsNoCategoriesStored() throws Exception{

        List<CategoryEntity> categoryEntities = new ArrayList<>();

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        mockMvc.perform(get("/categories/"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/categories/?page=-1",
            "/categories/?size=-1"
    })
    void expect_statusBadRequest_when_paginationParametersAreWrong(String url) throws Exception{

        mockMvc.perform(get(url))
                .andExpect(status().isBadRequest());
    }
}
