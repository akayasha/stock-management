package com.example.stockmanagement.test;

import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetItems() {
        Pageable pageable = PageRequest.of(0, 10);
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        Page<Item> page = new PageImpl<>(Arrays.asList(item));
        when(itemRepository.findAll(pageable)).thenReturn(page);

        Page<Item> result = itemService.getItems(pageable);
        assertEquals(1, result.getTotalElements());
        verify(itemRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSaveItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        when(itemRepository.save(item)).thenReturn(item);

        Item result = itemService.saveItem(item);
        assertEquals(item, result);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testGetItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Optional<Item> result = itemService.getItem(1L);
        assertTrue(result.isPresent());
        assertEquals(item, result.get());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteItem() {
        itemService.deleteItem(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);

        Item updatedItem = new Item();
        updatedItem.setName("Updated Item");
        updatedItem.setStock(100);

        Item result = itemService.updateItem(1L, updatedItem);
        assertEquals("Updated Item", result.getName());
        assertEquals(100, result.getStock());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }
}