package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void when_saveCategory_expect_callToPersistence() {
        Category category = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryPersistencePort.save(any(Category.class))).thenReturn(category);

        categoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

    @Test
    void save_shouldThrowInvalidLengthCategoryNameException_whenNameIsLong() {
        Category category = new Category(
                1L, "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN", "");

        assertThrows(InvalidLengthCategoryNameException.class, () -> categoryUseCase.save(category));
    }

    @Test
    void save_shouldThrowInvalidLengthCategoryDescriptionException_whenDescriptionIsLong() {
        Category category = new Category(
                1L, "Name",
                "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        assertThrows(InvalidLengthCategoryDescriptionException.class, () -> categoryUseCase.save(category));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(new Category(
                        1L, null,
                        "Desc")),
                Arguments.of(new Category(
                        1L, "",
                        "Desc")),
                Arguments.of(new Category(
                        1L, " ",
                        "Desc"))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty")
    void save_shouldThrowEmptyCategoryNameException_whenNameIsEmpty(Category category) {

        assertThrows(EmptyCategoryNameException.class, () -> categoryUseCase.save(category));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyCategoryDescriptionException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(new Category(
                        1L, "name",
                        null)),
                Arguments.of(new Category(
                        1L, "name",
                        "")),
                Arguments.of(new Category(
                        1L, "name",
                        " "))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyCategoryDescriptionException_whenNameIsEmpty")
    void save_shouldThrowEmptyCategoryDescriptionException_whenNameIsEmpty(Category category) {

        assertThrows(EmptyCategoryDescriptionException.class, () -> categoryUseCase.save(category));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 2, asc",
            "0, 1, desc",
            "0, 3, ''",
            "0, 6, null"
    }, nullValues = {"null"})
    void when_findAll_expect_callToPersistence(int page, int size, String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Electrónica", "Artículos Electrónicos"));
        categories.add(new Category(2L, "Ropa y Moda", "Prendas de vestir"));
        categories.add(new Category(3L, "Hogar y Cocina", "Artículos para el hogar"));
        categories.add(new Category(4L, "Deportes y Aire Libre", "Equipos deportivos"));

        if(sortDirection == null) {
            when(categoryPersistencePort.findAll(null)).thenReturn(categories);
        } else {
            when(categoryPersistencePort.findAll(anyString())).thenReturn(categories);
        }

        categoryUseCase.findAll(paginationRequest, sortDirection);

        verify(categoryPersistencePort, times(1)).findAll(sortDirection);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, asc",
            "1, -2, asc"
    })
    void expect_InvalidPaginationParametersException_when_pageOrSizeAreBad(int page, int size, String sort) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        assertThrows(InvalidPaginationParametersException.class, () -> {
            categoryUseCase.findAll(paginationRequest, sort);
        });

    }
}
