package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.mapper.ItemRequestMapper;
import com.emazon.stock_v1.domain.api.IItemServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemHandlerTest {

    @Mock
    private IItemServicePort itemServicePort;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @InjectMocks
    private ItemHandler itemHandler;

    private Item item;
    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx", 10,
                new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        itemRequest = new ItemRequest(
                item.getName(), item.getDescription(), item.getQuantity(), item.getPrice(),
                new BrandRequest(item.getBrand().getName(), item.getBrand().getDescription()),
                Collections.singleton(new CategoryRequest("Electrónica","Dispositivos tecnológicos")));
    }

    @Test
    void when_saveItem_expect_callToServicePort(){

        when(itemRequestMapper.itemRequestToItem(any(ItemRequest.class))).thenReturn(item);

        doNothing().when(itemServicePort).save(any(Item.class));

        itemHandler.save(itemRequest);

        verify(itemServicePort, times(1)).save(item);
    }
}
