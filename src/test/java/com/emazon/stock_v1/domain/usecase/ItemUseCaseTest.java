package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.domain.exception.BrandNotFoundException;
import com.emazon.stock_v1.domain.exception.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.emazon.stock_v1.utils.Helpers.isOrdered;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemUseCaseTest {

    @Mock
    private IItemPersistencePort itemPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    private Item item;
    private Brand brand;
    private Category category;

    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        category = new Category(1L, "Electrónica","Dispositivos tecnológicos");

        item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx", 10L,
                new BigDecimal(2000000),
                brand,
                Collections.singleton(category));
    }

    @Test
    void when_saveItem_expect_callToPersistence() {

        when(brandPersistencePort.findByName(anyString())).thenReturn(Optional.of(brand));

        when(categoryPersistencePort.findByName(anyString())).thenReturn(Optional.of(category));

        when(itemPersistencePort.save(item)).thenReturn(item);

        itemUseCase.save(item);

        verify(itemPersistencePort, times(1)).save(item);
    }

    @Test
    void expect_InvalidLengthItemNameException_when_nameIsLong() {
        item.setName("AsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsusAsus");

        assertThrows(InvalidLengthItemNameException.class, () -> itemUseCase.save(item));
    }

    @Test
    void expect_InvalidLengthItemDescription_when_descriptionIsLong() {
        item.setDescription("Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx" +
                "Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx" +
                "Disco duro: xx,  Ram: xx, Procesador: xxDisco duro: xx,  Ram: xx, Procesador: xx");

        assertThrows(InvalidLengthItemDescriptionException.class, () -> itemUseCase.save(item));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''",
            "' '",
            "null"
    }, nullValues = {"null"})
    void expect_EmptyItemNameException_when_nameIsEmpty(String name) {
        item.setName(name);

        assertThrows(EmptyItemNameException.class, () -> itemUseCase.save(item));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''",
            "' '",
            "null"
    }, nullValues = {"null"})
    void expect_EmptyItemDescriptionException_when_descriptionIsEmpty(String description) {
        item.setDescription(description);

        assertThrows(EmptyItemDescriptionException.class, () -> itemUseCase.save(item));
    }

    @Test
    void expect_EmptyItemBrandException_when_brandIsEmpty() {
        item.setBrand(null);

        assertThrows(EmptyBrandOfItemException.class, () -> itemUseCase.save(item));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''",
            "' '",
            "null"
    }, nullValues = {"null"})
    void expect_EmptyBrandNameException_when_brandNameIsEmpty(String brandName) {
        item.getBrand().setName(brandName);

        assertThrows(EmptyBrandNameException.class, () -> itemUseCase.save(item));
    }

    private static Stream<Arguments> providedExpect_InvalidNumOfCategoriesException_when_numOfCategoriesAreWrong() {
        return Stream.of(
                Arguments.of(Stream.of(
                        new Category(1L, "Electrónica","Dispositivos tecnológicos"),
                        new Category(1L, "Electrónica2","Dispositivos tecnológicos"),
                        new Category(1L, "Electrónica3","Dispositivos tecnológicos"),
                        new Category(1L, "Electrónica4","Dispositivos tecnológicos")
                ).collect(Collectors.toSet())),
                Arguments.of(new HashSet<>())
        );
    }

    @ParameterizedTest
    @MethodSource("providedExpect_InvalidNumOfCategoriesException_when_numOfCategoriesAreWrong")
    void expect_InvalidNumOfCategoriesException_when_numOfCategoriesAreWrong(Set<Category> categories) {
        item.setCategories(categories);

        assertThrows(InvalidNumOfCategoriesException.class, () -> itemUseCase.save(item));
    }

    private static Stream<Arguments> providedExpect_EmptyCategoryNameException_whenCategoryNameIsEmpty() {
        return Stream.of(
                Arguments.of(Stream.of(new Category(1L, "","Dispositivos tecnológicos")).collect(Collectors.toSet())),
                Arguments.of(Stream.of(new Category(1L, " ","Dispositivos tecnológicos")).collect(Collectors.toSet())),
                Arguments.of(Stream.of(new Category(1L, null,"Dispositivos tecnológicos")).collect(Collectors.toSet()))
        );
    }

    @ParameterizedTest
    @MethodSource("providedExpect_EmptyCategoryNameException_whenCategoryNameIsEmpty")
    void expect_EmptyCategoryNameException_whenCategoryNameIsEmpty(Set<Category> categories) {
        item.setCategories(categories);

        assertThrows(EmptyCategoryNameException.class, () -> itemUseCase.save(item));
    }

    @Test
    void expect_ItemHasDuplicateCategories_when_thereAreDuplicateCategories() {
        item.setCategories(Stream.of(
                new Category(1L, "Electrónica","Dispositivos tecnológicos"),
                new Category(1L, "Electrónica","Dispositivos tecnológicos")
        ).collect(Collectors.toSet()));

        assertThrows(ItemHasDuplicateCategories.class, () -> itemUseCase.save(item));
    }

    @Test
    void expect_BrandNotFoundException_when_brandDoesNotExists() {
        when(brandPersistencePort.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> itemUseCase.save(item));
    }

    @Test
    void expect_CategoryNotFoundException_when_categoryDoesNotExists() {
        when(brandPersistencePort.findByName(anyString())).thenReturn(Optional.of(brand));

        when(categoryPersistencePort.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> itemUseCase.save(item));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 2, , ,",
            "0, 2, , desc,",
            "0, 2, category, ,",
            "0, 2, category, asc, categories.name",
            "0, 2, brand, desc, brand.name",
            "0, 2, name, desc, name"
    }, nullValues = {"null"})
    void when_findAll_expect_callToPersistence(
            int page, int size, String sortProperty, String sortDirection, String expectSortProperty
    ) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Item> items = new ArrayList<>();
        items.add(item);

        if(isOrdered(sortProperty, sortDirection)) {
            when(itemPersistencePort.findAll(expectSortProperty, sortDirection)).thenReturn(items);
        } else {
            when(itemPersistencePort.findAll()).thenReturn(items);
        }

        itemUseCase.findAll(paginationRequest, sortProperty, sortDirection);

        if(isOrdered(sortProperty, sortDirection)) {
            verify(itemPersistencePort, times(1)).findAll(expectSortProperty, sortDirection);
        } else {
            verify(itemPersistencePort, times(1)).findAll();
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "name, desc"
    }, nullValues = {"null"})
    void when_findByCategory_expect_callToPersistence(String sortProperty, String sortDirection) {
        int page = 0, size = 10;
        Long categoryId = 1L;

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Item> items = new ArrayList<>();
        items.add(item);

        if(isOrdered(sortProperty, sortDirection)) {
            when(itemPersistencePort.findByCategoryId(categoryId, sortProperty, sortDirection)).thenReturn(items);
        } else {
            when(itemPersistencePort.findByCategoryId(categoryId)).thenReturn(items);
        }

        itemUseCase.findByCategory(categoryId, paginationRequest, sortProperty, sortDirection);

        if(isOrdered(sortProperty, sortDirection)) {
            verify(itemPersistencePort, times(1))
                    .findByCategoryId(categoryId, sortProperty, sortDirection);
        } else {
            verify(itemPersistencePort, times(1))
                    .findByCategoryId(categoryId);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "name, desc"
    }, nullValues = {"null"})
    void when_findByBrandName_expect_callToPersistence(String sortProperty, String sortDirection) {
        int page = 0, size = 10;
        String brandName = "Asus";

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Item> items = new ArrayList<>();
        items.add(item);

        if(isOrdered(sortProperty, sortDirection)) {
            when(itemPersistencePort.findByBrandName(brandName, sortProperty, sortDirection)).thenReturn(items);
        } else {
            when(itemPersistencePort.findByBrandName(brandName)).thenReturn(items);
        }

        itemUseCase.findByBrandName(brandName, paginationRequest, sortProperty, sortDirection);

        if(isOrdered(sortProperty, sortDirection)) {
            verify(itemPersistencePort, times(1))
                    .findByBrandName(brandName, sortProperty, sortDirection);
        } else {
            verify(itemPersistencePort, times(1)).findByBrandName(brandName);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2, name, asc",
            "1, -2, name, desc",
    })
    void expect_InvalidPaginationParametersException_when_pageOrSizeAreBad(
            int page, int size, String sortProperty, String sortDirection
    ) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        assertThrows(InvalidPaginationParametersException.class, () ->
                itemUseCase.findAll(paginationRequest, sortProperty, sortDirection));
    }

    @Test
    void expect_ItemsNotFoundException_when_thereAreNoItemsStored() {
        int page = 0, size = 10;
        String sortProperty = "", sortDirection = "asc";
        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        List<Item> items = new ArrayList<>();

        when(itemPersistencePort.findAll()).thenReturn(items);

        assertThrows(ItemsNotFoundException.class, () -> itemUseCase
                .findAll(paginationRequest, sortProperty, sortDirection));
    }

    @Test
    void when_increaseQuantity_expect_callToPersistence() {

        when(itemPersistencePort.findById(anyLong())).thenReturn(Optional.of(item));

        doNothing().when(itemPersistencePort).updateQuantity(anyLong(), anyLong());

        itemUseCase.increaseQuantity(item);

        verify(itemPersistencePort, times(1)).updateQuantity(anyLong(), anyLong());
    }

    @Test
    void expect_InvalidItemIdException_when_idInputIsZero() {
        item.setId(0L);

        assertThrows(InvalidItemIdException.class, () -> itemUseCase.increaseQuantity(item));
    }

    @Test
    void expect_InvalidItemQuantityException_when_idInputIsZero() {
        item.setQuantity(0L);

        assertThrows(InvalidItemQuantityException.class, () -> itemUseCase.increaseQuantity(item));
    }

    @Test
    void expect_ItemNotFoundException_when_itemDoesNotExist() {
        when(itemPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemUseCase.increaseQuantity(item));
    }
}
