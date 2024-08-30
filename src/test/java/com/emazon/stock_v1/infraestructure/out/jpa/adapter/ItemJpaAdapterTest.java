package com.emazon.stock_v1.infraestructure.out.jpa.adapter;

import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import com.emazon.stock_v1.infraestructure.exception.ItemAlreadyExistsException;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.BrandEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.CategoryEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.entity.ItemEntity;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
                10, new BigDecimal(2000000),
                brand,
                Collections.singleton(category));
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

    @DisplayName("Should throw ItemAlreadyExistsException when item exists")
    @Test
    void save_shouldThrowItemAlreadyExistsException_when_itemExists() {

        when(itemRepository.findByName(anyString())).thenReturn(Optional.of(itemEntity));

        assertThrows(ItemAlreadyExistsException.class, () -> itemJpaAdapter.save(item));
    }
}
