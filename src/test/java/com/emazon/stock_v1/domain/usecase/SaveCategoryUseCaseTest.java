package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.EmptyCategoryDescriptionException;
import com.emazon.stock_v1.domain.exception.EmptyCategoryNameException;
import com.emazon.stock_v1.domain.exception.InvalidLengthCategoryDescriptionException;
import com.emazon.stock_v1.domain.exception.InvalidLengthCategoryNameException;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveCategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private SaveCategoryUseCase saveCategoryUseCase;

    @Test
    @DisplayName("Should call categoryPersistencePort.save(category) one time")
    void saveTest() {

        Category category = new Category(1L, "Electrónica", "Artículos Electrónicos");

        when(categoryPersistencePort.save(any(Category.class))).thenReturn(category);

        saveCategoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

    private static Stream<Arguments> providedSave_shouldThrowInvalidLengthCategoryNameException_whenNameIsLong(){
        return Stream.of(
                Arguments.of(new Category(
                        1L,
                        "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
                        "Artículos Electrónicos")),
                Arguments.of(new Category(
                        1L,
                        " ",
                        "Artículos Electrónicos"))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowInvalidLengthCategoryNameException_whenNameIsLong")
    @DisplayName("Should throw InvalidLengthCategoryNameException when name is long")
    void save_shouldThrowInvalidLengthCategoryNameException_whenNameIsLong() {
        Category category = new Category(
                1L,
                "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
                "Artículos Electrónicos");

        assertThrows(InvalidLengthCategoryNameException.class, () -> {
            saveCategoryUseCase.save(category);
        });
    }

    private static Stream<Arguments> providedSave_shouldThrowInvalidLengthCategoryDescriptionException_whenDescriptionIsLong(){
        return Stream.of(
                Arguments.of(new Category(
                        1L,
                        "Electrónica",
                        "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                                "DescriptionDescription")),
                Arguments.of(new Category(
                        1L,
                        "Electrónica",
                        " "))
        );
    }

    @DisplayName("should throw InvalidLengthCategoryDescriptionException when description is long")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowInvalidLengthCategoryDescriptionException_whenDescriptionIsLong")
    void save_shouldThrowInvalidLengthCategoryDescriptionException_whenDescriptionIsLong(Category category) {
        assertThrows(InvalidLengthCategoryDescriptionException.class, () -> saveCategoryUseCase.save(category));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty(){
        return Stream.of(
                Arguments.of(new Category(1L, "", "")),
                Arguments.of(new Category(1L, null, ""))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty")
    @DisplayName("Should throw EmptyCategoryNameException when name is empty")
    void save_shouldThrowEmptyCategoryNameException_whenNameIsEmpty(Category category) {

        assertThrows(EmptyCategoryNameException.class, () -> {
            saveCategoryUseCase.save(category);
        });
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyCategoryDescriptionException_whenDescriptionIsEmpty(){
        return Stream.of(
                Arguments.of(new Category(1L, "Category", "")),
                Arguments.of(new Category(1L, "Category", null))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyCategoryDescriptionException_whenDescriptionIsEmpty")
    @DisplayName("Should throw EmptyCategoryDescriptionException when name is empty")
    void save_shouldThrowEmptyCategoryDescriptionException_whenDescriptionIsEmpty(Category category) {
        assertThrows(EmptyCategoryDescriptionException.class, () -> saveCategoryUseCase.save(category));
    }
}
