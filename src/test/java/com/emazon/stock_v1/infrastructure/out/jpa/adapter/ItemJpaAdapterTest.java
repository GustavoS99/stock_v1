package com.emazon.stock_v1.infrastructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.infrastructure.exception.ItemAlreadyExistsException;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infrastructure.out.jpa.entity.ItemEntity;
import com.emazon.stock_v1.infrastructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infrastructure.out.jpa.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemJpaAdapterTest {

    @Mock
    private IItemRepository itemRepository;

    @Mock
    static ItemEntityMapper itemEntityMapper;

    @InjectMocks
    private ItemJpaAdapter itemJpaAdapter;

    private Item item;
    private ItemEntity itemEntity;
    private List<ItemEntity> itemEntities;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        BrandEntity brandEntity = new BrandEntity(
                1L, "Asus", "Hardware de informática y electrónica de consumo.");

        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electrónica", "Dispositivos tecnológicos");

        itemEntity = new ItemEntity(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10L, new BigDecimal(2000000),
                brandEntity,
                Collections.singleton(categoryEntity));

        Brand brand = new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo.");

        Category category = new Category(1L, "Electrónica", "Dispositivos tecnológicos");

        item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx",
                10L, new BigDecimal(2000000),
                brand,
                Collections.singleton(category));

        itemEntities = new ArrayList<>();
        itemEntities.add(itemEntity);

        items = new ArrayList<>();
        items.add(item);
    }

    @Test
    void when_saveItem_expect_fieldsSavedSuccessfully() {

        when(itemEntityMapper.itemToItemEntity(any(Item.class))).thenReturn(itemEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        when(itemEntityMapper.itemEntityToItem(any(ItemEntity.class))).thenReturn(item);

        Item savedItem = itemJpaAdapter.save(item);

        assertNotNull(savedItem, "The result should not be null");
        assertEquals(item.getId(), savedItem.getId());
        assertEquals(item.getName(), savedItem.getName());
        assertEquals(item.getDescription(), savedItem.getDescription());
        assertEquals(item.getPrice(), savedItem.getPrice());
        assertEquals(item.getQuantity(), savedItem.getQuantity());
    }

    @Test
    void save_shouldThrowItemAlreadyExistsException_when_itemExists() {

        when(itemRepository.findByName(anyString())).thenReturn(Optional.of(itemEntity));

        assertThrows(ItemAlreadyExistsException.class, () -> itemJpaAdapter.save(item));
    }

    @Test
    void when_findAll_expect_callToRepository() {

        when(itemRepository.findAll()).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findAll();

        assertEquals(items, result);

        verify(itemRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @CsvSource({
            "categories.name, desc",
            "brand.name, desc"
    })
    void when_findAllOrdered_expect_callToRepository(String sortProperty, String sortDirection) {

        when(itemRepository.findAll(any(Sort.class))).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findAll(sortProperty, sortDirection);

        assertEquals(items, result);

        verify(itemRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void when_findByCategoryId_expect_callToRepository() {
        Long categoryId = 1L;

        when(itemRepository.findByCategoriesId(anyLong())).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findByCategoryId(categoryId);

        assertEquals(items, result);

        verify(itemRepository, times(1)).findByCategoriesId(categoryId);
    }

    @ParameterizedTest
    @CsvSource({
            "categories.name, desc",
            "brand.name, desc"
    })
    void when_findByCategoryIdOrdered_expect_callToRepository(String sortProperty, String sortDirection) {
        Long categoryId = 1L;

        when(itemRepository.findByCategoriesId(anyLong(), any(Sort.class))).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findByCategoryId(categoryId, sortProperty, sortDirection);

        assertEquals(items, result);

        verify(itemRepository, times(1)).findByCategoriesId(anyLong(), any(Sort.class));
    }

    @Test
    void when_findByBrandName_expect_callToRepository() {
        String brandName = "Asus";

        when(itemRepository.findByBrandName(anyString())).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findByBrandName(brandName);

        assertEquals(items, result);

        verify(itemRepository, times(1)).findByBrandName(brandName);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "categories.name, desc",
            "brand.name, desc"
    })
    void when_findByBrandNameOrdered_expect_callToRepository(String sortProperty, String sortDirection) {
        String brandName = "Asus";

        when(itemRepository.findByBrandName(anyString(), any(Sort.class))).thenReturn(itemEntities);

        when(itemEntityMapper.itemEntitiesToItems(anyList())).thenReturn(items);

        List<Item> result = itemJpaAdapter.findByBrandName(brandName, sortProperty, sortDirection);

        assertEquals(items, result);

        verify(itemRepository, times(1)).findByBrandName(anyString(), any(Sort.class));
    }

    @Test
    void when_updateQuantity_expect_callToRepository() {

        doNothing().when(itemRepository).updateQuantity(anyLong(), anyLong());

        itemJpaAdapter.updateQuantity(item.getId(), item.getQuantity());

        verify(itemRepository, times(1)).updateQuantity(anyLong(), anyLong());
    }
}
