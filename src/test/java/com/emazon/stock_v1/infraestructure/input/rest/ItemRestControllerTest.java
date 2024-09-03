package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.handler.IItemHandler;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.ItemEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ItemRestControllerTest {

    @SpyBean
    private IItemHandler itemHandler;

    @MockBean
    private IItemRepository itemRepository;

    @MockBean
    private IBrandRepository brandRepository;

    @MockBean
    private ICategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    private String itemJson;
    private ItemEntity itemEntity;
    private BrandEntity brandEntity;
    private CategoryEntity categoryEntity;
    private List<ItemEntity> itemEntities;

    @BeforeEach
    void setUp() {
        itemJson = """
                {
                "name" : "Portatil XYZ",
                "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                "quantity" : 10,
                "price" : 2000000,
                "brand" : { "name" : "Asus" },
                "categories" : [ { "name" : "Electrónica" } ]
                }
                """;

        brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");
        categoryEntity = new CategoryEntity(1L, "Electrónica","Dispositivos tecnológicos");
        itemEntity = new ItemEntity(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10L, new BigDecimal(2000000),
                brandEntity,
                Collections.singleton(categoryEntity));

        itemEntities = new ArrayList<>();

        itemEntities.add(itemEntity);
    }

    @Test
    void when_save_expect_createdStatus() throws Exception {

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(categoryEntity));

        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);

        mockMvc.perform(post("/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJson))
                .andExpect(status().isCreated());
    }

    private static Stream<Arguments> providedSave_shouldReturnBadRequest_whenValidationFails() {
        return Stream.of(
                Arguments.of("""
                        {
                        "name" : "",
                        "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : null,
                        "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : " ",
                        "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : null,
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : null,
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "namenamenamenamenamenamenamenamenamenamenamenamename",
                        "description" : "desc",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription
                        descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription
                        descriptiondescription",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : null,
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : null },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "" },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : " " },
                        "categories" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : null
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ null ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : null } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : " " } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [ { "name" : "Electrónica" }, { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brand" : { "name" : "Asus" },
                        "categories" : [
                            { "name" : "Electrónica1" },
                            { "name" : "Electrónica2" },
                            { "name" : "Electrónica3" },
                            { "name" : "Electrónica4" } ]
                        }
                        """)
        );
    }

    @DisplayName("Should verify the expected 400 status")
    @ParameterizedTest
    @MethodSource("providedSave_shouldReturnBadRequest_whenValidationFails")
    void save_shouldReturnBadRequest_whenValidationFails(String itemExceptionJson) throws Exception {
        doCallRealMethod().when(itemHandler).save(any(ItemRequest.class));

        mockMvc.perform(post("/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemExceptionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void expect_ConflictStatus_when_itemExists() throws Exception {

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(categoryEntity));

        when(itemRepository.findByName(anyString())).thenReturn(Optional.of(itemEntity));

        mockMvc.perform(post("/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJson))
                .andExpect(status().isConflict());
    }

    @Test
    void expect_NotFoundStatus_when_brandDoesNotExist() throws Exception {
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void expect_NotFoundStatus_when_categoryDoesNotExist() throws Exception {

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/",
            "/items/?page=0",
            "/items/?size=6",
            "/items/?page=0&size=3"
    })
    void when_findAll_expect_statusOk(String url) throws Exception {

        when(itemRepository.findAll()).thenReturn(itemEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/?sortBy=name",
            "/items/?sortBy=name&sortDirection=desc",
            "/items/?page=0&sortBy=name&sortDirection=desc",
            "/items/?page=0&size=5&sortBy=name&sortDirection=desc"
    })
    void when_findAllOrdered_expect_statusOk(String url) throws Exception {

        when(itemRepository.findAll(any(Sort.class))).thenReturn(itemEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/category/1",
            "/items/category/1?page=0",
            "/items/category/1?size=8",
            "/items/category/1?page=0&size=8"
    })
    void when_findByCategory_expect_statusOk(String url) throws Exception {

        when(itemRepository.findByCategoriesId(anyLong())).thenReturn(itemEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/category/1?sortBy=name",
            "/items/category/1?sortBy=name&sortDirection=desc",
            "/items/category/1?page=0&sortBy=name&sortDirection=desc",
            "/items/category/1?size=8&sortBy=name&sortDirection=desc",
            "/items/category/1?page=0&size=8&sortBy=name&sortDirection=desc"
    })
    void when_findByCategoryOrdered_expect_statusOk(String url) throws Exception {

        when(itemRepository.findByCategoriesId(anyLong(), any(Sort.class))).thenReturn(itemEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/brand/, Asus",
            "/items/brand/?page=0, Asus",
            "/items/brand/?size=8, Asus",
            "/items/brand/?page=0&size=8, Asus"
    })
    void when_findByBrand_expect_statusOk(String url, String brandName) throws Exception {
        String brandJson = """
                { "name" :\"""" + brandName + """
                "}
                """;

        when(itemRepository.findByBrandName(anyString())).thenReturn(itemEntities);

        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/items/brand/?sortBy=name, Asus",
            "/items/brand/?sortBy=name&sortDirection=desc, Asus",
            "/items/brand/?page=0&sortBy=name&sortDirection=desc, Asus",
            "/items/brand/?size=8&sortBy=name&sortDirection=desc, Asus",
            "/items/brand/?page=0&size=8&sortBy=name&sortDirection=desc, Asus"
    })
    void when_findByBrandOrdered_expect_statusOk(String url, String brandName) throws Exception {
        String brandJson = """
                { "name" :\"""" + brandName + """
                "}
                """;

        when(itemRepository.findByBrandName(anyString(), any(Sort.class))).thenReturn(itemEntities);

        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isOk());
    }

    @Test
    void expect_notFoundStatus_when_itemsNotFound() throws Exception {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/items/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void expect_badRequestStatus_when_pageExceedTotalPages() throws Exception {
        when(itemRepository.findAll()).thenReturn(itemEntities);

        mockMvc.perform(get("/items/?page=8"))
                .andExpect(status().isBadRequest());
    }
}
