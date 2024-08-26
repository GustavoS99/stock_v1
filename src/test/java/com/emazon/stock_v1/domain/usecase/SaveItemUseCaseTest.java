package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveItemUseCaseTest {

    @Mock
    private IItemPersistencePort itemPersistencePort;

    @InjectMocks
    private SaveItemUseCase saveItemUseCase;

    @DisplayName("Should call BrandPersistencePort.save(Brand) one time")
    @Test
    void save_shouldCallBrandPersistencePort() {
        Item item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10, new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        when(itemPersistencePort.save(any(Item.class))).thenReturn(item);

        saveItemUseCase.save(item);

        verify(itemPersistencePort, times(1)).save(any(Item.class));
    }

    @DisplayName("Should throw InvalidLengthItemNameException when name is long")
    @Test
    void save_shouldThrowInvalidLengthItemNameException_whenNameIsLong() {
        Item item = new Item(
                1L,
                "Portatil XYZPortatil XYZPortatil XYZPortatil XYZPortatil XYZ",
                "Description",
                10, new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(
                        new Category(1L, "Electrónica","Dispositivos tecnológicos")));
        assertThrows(InvalidLengthItemNameException.class, () -> saveItemUseCase.save(item));
    }

    @DisplayName("Should throw InvalidLengthItemDescription when description is long")
    @Test
    void save_shouldThrowInvalidLengthItemDescriptionException_whenDescriptionIsLong() {
        Item item = new Item(
                1L,
                "Portatil XYZ",
                "Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx" +
                        "Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx" +
                        "Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx",
                10, new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(
                        new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        assertThrows(InvalidLengthItemDescriptionException.class, () -> saveItemUseCase.save(item));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyItemNameException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(
                        new Item(1L, "", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                ),
                Arguments.of(
                        new Item(1L, null, "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyItemNameException_whenNameIsEmpty")
    @DisplayName("Should throw EmptyItemNameException when name is empty")
    void save_shouldThrowEmptyItemNameException_whenNameIsEmpty(Item item) {
        assertThrows(EmptyItemNameException.class, () -> saveItemUseCase.save(item));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyItemDescriptionException_whenDescriptionIsEmpty() {
        return Stream.of(
                Arguments.of(
                        new Item(1L, "null", "",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                ),
                Arguments.of(
                        new Item(1L, "null", null,
                                10, new BigDecimal(2000000),
                                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyItemDescriptionException_whenDescriptionIsEmpty")
    @DisplayName("Should throw EmptyItemDescriptionException when description is empty")
    void save_shouldThrowEmptyItemDescriptionException_whenDescriptionIsEmpty(Item item) {
        assertThrows(EmptyItemDescriptionException.class, () -> saveItemUseCase.save(item));
    }

    @DisplayName("Should throw EmptyItemBrandException when brand is empty")
    @Test
    void save_shouldThrowEmptyItemBrandException_whenBrandIsEmpty() {
        Item item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10, new BigDecimal(2000000),
                null,
                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        assertThrows(EmptyBrandOfItemException.class, () -> saveItemUseCase.save(item));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyBrandNameException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "", ""),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                ),
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, null, ""),
                                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")))
                )
        );
    }

    @DisplayName("Should throw EmptyBrandNameException when brand name is empty")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyBrandNameException_whenNameIsEmpty")
    void save_shouldThrowEmptyBrandNameException_whenNameIsEmpty(Item item) {
        assertThrows(EmptyBrandNameException.class, () -> saveItemUseCase.save(item));
    }

    private static Stream<Arguments> providedSave_shouldThrowInvalidNumOfCategories_whenNumOfCategoriesAreWrong(){
        return Stream.of(
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "null", "test"),
                                new HashSet<>())
                ),
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "null", "test"),
                                Stream.of(
                                        new Category(1L, "Electrónica","Dispositivos tecnológicos"),
                                        new Category(1L, "Electrónica2","Dispositivos tecnológicos"),
                                        new Category(1L, "Electrónica3","Dispositivos tecnológicos"),
                                        new Category(1L, "Electrónica4","Dispositivos tecnológicos")
                                ).collect(Collectors.toSet())
                        )
                )
        );
    }

    @DisplayName("Should throw InvalidNumOfCategories when the num of categories are wrong")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowInvalidNumOfCategories_whenNumOfCategoriesAreWrong")
    void save_shouldThrowInvalidNumOfCategories_whenNumOfCategoriesAreWrong(Item item) {
        assertThrows(InvalidNumOfCategories.class, () -> saveItemUseCase.save(item));
    }

    private static Stream<Arguments> providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty() {
        return Stream.of(
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "null", ""),
                                Collections.singleton(new Category(1L, "","Dispositivos tecnológicos")))
                ),
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "null", ""),
                                Collections.singleton(new Category(1L, null,"Dispositivos tecnológicos")))
                ),
                Arguments.of(
                        new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                                10, new BigDecimal(2000000),
                                new Brand(1L, "null", ""),
                                Collections.singleton(new Category(1L, " ","Dispositivos tecnológicos")))
                )
        );
    }

    @DisplayName("Should throw EmptyCategoryNameException when category name is empty")
    @ParameterizedTest
    @MethodSource("providedSave_shouldThrowEmptyCategoryNameException_whenNameIsEmpty")
    void save_shouldThrowEmptyCategoryNameException_whenNameIsEmpty(Item item) {
        assertThrows(EmptyCategoryNameException.class, () -> saveItemUseCase.save(item));
    }

    @DisplayName("Should throw ItemHasDuplicateCategories when therea are duplicate categories")
    @Test
    void save_shouldThrowItemHasDuplicateCategories_whenThereAreDuplicateCategories() {
        Item item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10, new BigDecimal(2000000),
                new Brand(1L, "null", "test"),
                Stream.of(
                        new Category(1L, "Electrónica","Dispositivos tecnológicos"),
                        new Category(1L, "Electrónica","Dispositivos tecnológicos")
                ).collect(Collectors.toSet())
        );

        assertThrows(ItemHasDuplicateCategories.class, () -> saveItemUseCase.save(item));
    }
}
