package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.BrandRequest;
import com.emazon.stock_v1.application.dto.CategoryRequest;
import com.emazon.stock_v1.application.dto.ItemRequest;
import com.emazon.stock_v1.application.mapper.ItemRequestMapper;
import com.emazon.stock_v1.domain.api.ISaveItemServicePort;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.Category;
import com.emazon.stock_v1.domain.model.Item;
import org.junit.jupiter.api.DisplayName;
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
    private ISaveItemServicePort saveItemServicePort;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @InjectMocks
    private ItemHandler itemHandler;

    @DisplayName("Should verify the call to SaveItemServicePort.save(Item)")
    @Test
    void saveTest(){
        ItemRequest itemRequest = new ItemRequest(
                "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx", 10,
                new BigDecimal(2000000),
                new BrandRequest("Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(new CategoryRequest("Electrónica","Dispositivos tecnológicos")));

        Item item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx", 10,
                new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        when(itemRequestMapper.itemRequestToItem(any(ItemRequest.class))).thenReturn(item);

        doNothing().when(saveItemServicePort).save(any(Item.class));

        itemHandler.save(itemRequest);

        verify(saveItemServicePort, times(1)).save(item);
    }
}
