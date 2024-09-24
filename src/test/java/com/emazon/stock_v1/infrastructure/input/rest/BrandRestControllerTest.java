package com.emazon.stock_v1.infrastructure.input.rest;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.handler.IBrandHandler;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infrastructure.out.jpa.repository.IBrandRepository;
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
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.emazon.stock_v1.utils.GlobalConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BrandRestControllerTest {

    private static final String ADMIN_USERNAME = "1,gustavo.salazar.co@gmail.com";
    private static final String WORKER_USERNAME = "2,ronaldo@email.com";
    private static final String CUSTOMER_USERNAME = "3,messi@email.com";

    @SpyBean
    private IBrandHandler brandHandler;

    @MockBean
    private IBrandRepository brandRepository;

    @Autowired
    private MockMvc mockMvc;

    private String brandJson;
    private BrandEntity brandEntity;

    @BeforeEach
    void setUp() {
        brandJson = """
                {
                "name": "Asus",
                "description": "Hardware de informática y electrónica de consumo."
                }
                """;

        brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = ADMIN)
    void when_save_expect_createdStatus() throws Exception {

        when(brandRepository.save(any(BrandEntity.class))).thenReturn(brandEntity);

        mockMvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isCreated());
    }

    private static Stream<Arguments> providedSave_shouldReturnBadRequest_whenValidationFails() {
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
                        """),
                Arguments.of("{}")
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldReturnBadRequest_whenValidationFails")
    @WithMockUser(username = ADMIN_USERNAME, roles = ADMIN)
    void save_shouldReturnBadRequest_whenValidationFails(String brandJson) throws Exception {

        doCallRealMethod().when(brandHandler).save(any(BrandRequest.class));

        mockMvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = ADMIN)
    void expect_ConflictStatus_when_brandExists() throws Exception {

        Optional<BrandEntity> optionalBrandEntity = Optional.of(brandEntity);

        when(brandRepository.findByName(anyString())).thenReturn(optionalBrandEntity);

        mockMvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void when_findAll_expect_statusOk() throws Exception {

        List<BrandEntity> brandEntities = new ArrayList<>();
        brandEntities.add(
                new BrandEntity(1L, "Asus", "Hardware de informática y electrónica de consumo."));

        when(brandRepository.findAll()).thenReturn(brandEntities);

        mockMvc.perform(get("/brands/"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/brands/?sortDirection=desc",
            "/brands/?sortDirection=asc",
    })
    @WithMockUser(username = CUSTOMER_USERNAME, roles = CUSTOMER)
    void when_findAllOrdered_expect_statusOk(String url) throws Exception {
        List<BrandEntity> brandEntities = new ArrayList<>();

        brandEntities.add(brandEntity);

        when(brandRepository.findAll(any(Sort.class))).thenReturn(brandEntities);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = WORKER_USERNAME, roles = WAREHOUSE_WORKER)
    void findAll_shouldThrowBrandsNotFoundException() throws Exception {

        List<BrandEntity> brandEntities = new ArrayList<>();

        when(brandRepository.findAll()).thenReturn(brandEntities);

        mockMvc.perform(get("/brands/"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/brands/?page=-1",
            "/brands/?size=-1"
    })
    @WithMockUser(username = CUSTOMER_USERNAME, roles = CUSTOMER)
    void expect_statusBadRequest_when_paginationParametersAreWrong(String url) throws Exception{

        mockMvc.perform(get(url))
                .andExpect(status().isBadRequest());
    }
}
