package com.emazon.stock_v1.domain.usecase;

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

    private static Stream<Arguments> providedSaveTest(){
        return Stream.of(
                Arguments.of(new Category(1L, "Electrónica", "Artículos Electrónicos")),
                Arguments.of(new Category(1L, "Electrónica", "")),
                Arguments.of(new Category(1L, "Electrónica", null))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSaveTest")
    @DisplayName("Should call categoryPersistencePort.save(category) one time")
    void saveTest(Category category) {

        when(categoryPersistencePort.save(any(Category.class))).thenReturn(category);

        saveCategoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

    @Test
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

    @Test
    @DisplayName("should throw InvalidLengthCategoryDescriptionException when description is long")
    void save_shouldThrowInvalidLengthCategoryDescriptionException_whenDescriptionIsLong() {
        Category category = new Category(
                1L,
                "name",
                "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondes");

        assertThrows(InvalidLengthCategoryDescriptionException.class, () -> {
            saveCategoryUseCase.save(category);
        });
    }
}
