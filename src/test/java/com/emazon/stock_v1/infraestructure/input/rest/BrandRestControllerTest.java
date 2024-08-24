package com.emazon.stock_v1.infraestructure.input.rest;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.handler.IBrandHandler;
import com.emazon.stock_v1.helpers.GlobalConstants;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
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
import org.springframework.data.domain.*;
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
class BrandRestControllerTest {

    @SpyBean
    private IBrandHandler brandHandler;

    @MockBean
    private IBrandRepository brandRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should verify the expected 201 status")
    void saveTest() throws Exception {
        String brandJson = """
                {
                "name": "Asus",
                "description": "Hardware de informática y electrónica de consumo."
                }
                """;

        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");

        when(brandRepository.save(any(BrandEntity.class))).thenReturn(brandEntity);

        mockMvc.perform(post("/brands/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isCreated());
    }

    private static Stream<Arguments> providedBrandRequestsForSaveBrandException(){
        return Stream.of(
                Arguments.of("""
                        {
                        "name": "",
                        "description": "",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": null,
                        "description": "",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": " ",
                        "description": "",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": "name",
                        "description": "",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": "name",
                        "description": null,
                        }
                        """),
                Arguments.of("""
                        {
                        "name": "name",
                        "description": " ",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": "namenamenamenamenamenamenamenamenamenamenamenamename",
                        "description": " ",
                        }
                        """),
                Arguments.of("""
                        {
                        "name": "name",
                        "description": "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription
                        descriptiondescriptiondescriptiondescriptiondescriptiondescription",
                        }
                        """)
        );
    }

    @DisplayName("Should verify the expected 400 status")
    @ParameterizedTest
    @MethodSource("providedBrandRequestsForSaveBrandException")
    void saveBrandBadRequestExceptionTest(String brandJson) throws Exception {

        doCallRealMethod().when(brandHandler).save(any(BrandRequest.class));

        mockMvc.perform(post("/brands/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Should verify the expected 409 status")
    @Test
    void saveBrandConflictExceptionTest() throws Exception {
        String brandJson = """
                {
                "name": "Asus",
                "description": "Hardware de informática y electrónica de consumo."
                }
                """;

        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");

        Optional<BrandEntity> optionalBrandEntity = Optional.of(brandEntity);

        when(brandRepository.findByName(anyString())).thenReturn(optionalBrandEntity);

        mockMvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isConflict());
    }

    @DisplayName("Should verify the expected 200 status")
    @Test
    void findAllTest() throws Exception {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).ascending());

        List<BrandEntity> brandEntityList = new ArrayList<>();
        brandEntityList.add(
                new BrandEntity(1L, "Asus", "Hardware de informática y electrónica de consumo."));

        Page<BrandEntity> brandEntities = new PageImpl<>(brandEntityList, pageable, brandEntityList.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntities);

        mockMvc.perform(get("/brands/"))
                .andExpect(status().isOk());
    }

    @DisplayName("Should verify the expected 400 status when brands not found")
    @Test
    void findAll_shouldThrowBrandsNotFoundException() throws Exception {

        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).ascending());
        List<BrandEntity> brandEntityList = new ArrayList<>();
        Page<BrandEntity> brandEntities = new PageImpl<>(brandEntityList, pageable, brandEntityList.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntities);

        mockMvc.perform(get("/brands/"))
                .andExpect(status().isNotFound());
    }
}
