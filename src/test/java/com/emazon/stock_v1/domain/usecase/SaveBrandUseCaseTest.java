package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.EmptyBrandDescriptionException;
import com.emazon.stock_v1.domain.exception.EmptyBrandNameException;
import com.emazon.stock_v1.domain.exception.InvalidLengthBrandDescriptionException;
import com.emazon.stock_v1.domain.exception.InvalidLengthBrandNameException;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveBrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private SaveBrandUseCase saveBrandUseCase;

    @DisplayName("Should call brandPersistencePort.save(brand) one time")
    @Test
    void saveTest(){
        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        when(brandPersistencePort.save(brand)).thenReturn(brand);

        saveBrandUseCase.save(brand);

        verify(brandPersistencePort, times(1)).save(brand);
    }

    private static Stream<Arguments> providedSave_shouldThrowInvalidLengthBrandNameException_whenNameIsLong() {
        return Stream.of(
                Arguments.of(new Brand(
                        1L,
                        "AsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsus",
                        "Description")),
                Arguments.of(new Brand(
                        1L,
                        " ",
                        "Description"))
        );
    }

    @DisplayName("Should throw InvalidLengthBrandNameException when name is long")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowInvalidLengthBrandNameException_whenNameIsLong")
    void save_shouldThrowInvalidLengthBrandNameException_whenNameIsLong(Brand brand) {

        assertThrows(InvalidLengthBrandNameException.class, () -> saveBrandUseCase.save(brand));
    }

    private static Stream<Arguments> providedSave_shouldThrowInvalidLengthBrandDescriptionException_whenDescriptionIsLong(){
        return Stream.of(
                Arguments.of(new Brand(
                        1L,
                        "Asus",
                        "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription")),
                Arguments.of(new Brand(
                        1L,
                        "Asus",
                        " "))
        );
    }

    @DisplayName("Should throw InvalidLengthBrandDescriptionException when description is long")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowInvalidLengthBrandDescriptionException_whenDescriptionIsLong")
    void save_shouldThrowInvalidLengthBrandDescriptionException_whenDescriptionIsLong(Brand brand) {

        assertThrows(InvalidLengthBrandDescriptionException.class, () -> saveBrandUseCase.save(brand));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyBrandNameException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(new Brand(1L, "", "")),
                Arguments.of(new Brand(1L, null, ""))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyBrandNameException_whenNameIsEmpty")
    @DisplayName("Should throw EmptyBrandNameException when name is empty")
    void save_shouldThrowEmptyBrandNameException_whenNameIsEmpty(Brand brand) {
        assertThrows(EmptyBrandNameException.class, () -> saveBrandUseCase.save(brand));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyBrandDescriptionException_whenDescriptionIsEmpty(){
        return Stream.of(
                Arguments.of(new Brand(1L, "Asus", "")),
                Arguments.of(new Brand(1L, "Asus", null))
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyBrandDescriptionException_whenDescriptionIsEmpty")
    @DisplayName("")
    void save_shouldThrowEmptyBrandDescriptionException_whenDescriptionIsEmpty(Brand brand) {
        assertThrows(EmptyBrandDescriptionException.class, () -> saveBrandUseCase.save(brand));
    }
}
