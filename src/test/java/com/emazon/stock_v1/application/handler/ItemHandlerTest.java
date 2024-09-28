package com.emazon.stock_v1.application.handler;

import com.emazon.stock_v1.application.dto.*;
import com.emazon.stock_v1.application.mapper.ItemRequestMapper;
import com.emazon.stock_v1.application.mapper.ItemResponseMapper;
import com.emazon.stock_v1.application.mapper.ItemUpdateQuantityRequestMapperImpl;
import com.emazon.stock_v1.domain.api.IItemServicePort;
import com.emazon.stock_v1.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemHandlerTest {

    @Mock
    private IItemServicePort itemServicePort;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @Mock
    private ItemResponseMapper itemResponseMapper;

    @Spy
    private ItemUpdateQuantityRequestMapperImpl itemUpdateQuantityRequestMapper;

    @InjectMocks
    private ItemHandler itemHandler;

    private Item item;
    private ItemRequest itemRequest;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "Portatil XYZ", "Disco duro: xx,  Ram: xx, Procesador: xx", 10L,
                new BigDecimal(2000000),
                new Brand(1L, "Asus", "Hardware de informática y electrónica de consumo."),
                Collections.singleton(new Category(1L, "Electrónica","Dispositivos tecnológicos")));

        itemRequest = new ItemRequest(
                item.getName(), item.getDescription(), item.getQuantity(), item.getPrice(),
                new BrandRequest(item.getBrand().getName(), item.getBrand().getDescription()),
                Collections.singleton(new CategoryRequest("Electrónica","Dispositivos tecnológicos")));

        itemResponse = new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getQuantity(),
                item.getPrice(), new BrandResponse(item.getBrand().getName(), item.getBrand().getDescription()),
                Collections.singleton(
                        new CategoryItemResponse(1L, "Electrónica")));
    }

    @Test
    void when_saveItem_expect_callToServicePort(){

        when(itemRequestMapper.itemRequestToItem(any(ItemRequest.class))).thenReturn(item);

        doNothing().when(itemServicePort).save(any(Item.class));

        itemHandler.save(itemRequest);

        verify(itemServicePort, times(1)).save(item);
    }

    @Test
    void when_findAllItems_expect_callToServicePort() {

        int page = 0, size = 10;
        String sortProperty = "", sortDirection = "asc";

        List<Item> itemList = Collections.singletonList(item);

        PaginatedResult<Item> items = new PaginatedResult<>(itemList, page, size, itemList.size());

        List<ItemResponse> expectedItems = Collections.singletonList(itemResponse);

        PaginatedResult<ItemResponse> itemResponses = new PaginatedResult<>(
                expectedItems, page, size, expectedItems.size()
        );

        when(itemServicePort.findAll(any(PaginationRequest.class), anyString(), anyString())).thenReturn(items);

        when(itemResponseMapper.itemsToItemResponses(any())).thenReturn(itemResponses);

        PaginatedResult<ItemResponse> result = itemHandler.findAll(page, size, sortProperty, sortDirection);

        assertEquals(expectedItems.get(0).getName(), result.getItems().get(0).getName());
        assertEquals(expectedItems.get(0).getDescription(), result.getItems().get(0).getDescription());
        assertEquals(expectedItems.get(0).getQuantity(), result.getItems().get(0).getQuantity());
        assertEquals(expectedItems.get(0).getPrice(), result.getItems().get(0).getPrice());
        assertEquals(expectedItems.get(0).getBrand().getName(), result.getItems().get(0).getBrand().getName());
        verify(itemServicePort, times(1)).findAll(
                any(PaginationRequest.class), anyString(), anyString());
    }

    @Test
    void when_increaseQuantity_expect_callToServicePort() {
        ItemUpdateQuantityRequest itemUpdateQuantityRequest = ItemUpdateQuantityRequest.builder()
                .id(1L)
                .quantity(30L)
                .build();

        doCallRealMethod().when(itemUpdateQuantityRequestMapper).toItem(any(ItemUpdateQuantityRequest.class));

        itemHandler.increaseQuantity(itemUpdateQuantityRequest);

        verify(itemServicePort, times(1)).increaseQuantity(any(Item.class));
    }
}
