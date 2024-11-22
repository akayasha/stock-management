package com.example.stockmanagement.test;

import com.example.stockmanagement.entity.Inventory;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.InventoryRepository;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.service.InventoryService;
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

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInventory() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setQuantity(10);
        inventory.setType("T");
        inventory.setItem(item);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        when(itemRepository.save(item)).thenReturn(item);

        Inventory result = inventoryService.saveInventory(inventory);
        assertEquals(inventory, result);
        verify(inventoryRepository, times(1)).save(inventory);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testGetInventories() {
        Pageable pageable = PageRequest.of(0, 10);
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setQuantity(10);
        inventory.setType("T");

        Page<Inventory> page = new PageImpl<>(Arrays.asList(inventory));
        when(inventoryRepository.findAll(pageable)).thenReturn(page);

        Page<Inventory> result = inventoryService.getInventories(pageable);
        assertEquals(1, result.getTotalElements());
        verify(inventoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setQuantity(10);
        inventory.setType("T");

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        Inventory result = inventoryService.getInventory(1L);
        assertEquals(inventory, result);
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteInventory() {
        inventoryService.deleteInventory(1L);
        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setQuantity(10);
        inventory.setType("T");

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(inventory)).thenReturn(inventory);

        Inventory updatedInventory = new Inventory();
        updatedInventory.setQuantity(20);
        updatedInventory.setType("W");

        Inventory result = inventoryService.updateInventory(1L, updatedInventory);
        assertEquals(20, result.getQuantity());
        assertEquals("W", result.getType());
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(inventory);
    }
}