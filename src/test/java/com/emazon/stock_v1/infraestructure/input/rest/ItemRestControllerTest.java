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
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setUp() {
        itemJson = """
                {
                "name" : "Portatil XYZ",
                "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                "quantity" : 10,
                "price" : 2000000,
                "brandRequest" : { "name" : "Asus" },
                "categoryRequests" : [ { "name" : "Electrónica" } ]
                }
                """;

        brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");
        categoryEntity = new CategoryEntity(1L, "Electrónica","Dispositivos tecnológicos");
        itemEntity = new ItemEntity(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10L, new BigDecimal(2000000),
                brandEntity,
                Collections.singleton(categoryEntity));
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
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : null,
                        "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : " ",
                        "description" : "Disco duro: xx,  Ram: xx, Procesador: xx",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : null,
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : null,
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "namenamenamenamenamenamenamenamenamenamenamenamename",
                        "description" : "desc",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
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
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : null,
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : null },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "" },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : " " },
                        "categoryRequests" : [ { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : null
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ null ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : null } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : " " } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [ { "name" : "Electrónica" }, { "name" : "Electrónica" } ]
                        }
                        """),
                Arguments.of("""
                        {
                        "name" : "name",
                        "description" : "description",
                        "quantity" : 10,
                        "price" : 2000000,
                        "brandRequest" : { "name" : "Asus" },
                        "categoryRequests" : [
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
}
