package com.example.stockmanagement.test;

import com.example.stockmanagement.entity.Inventory;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.entity.Orders;
import com.example.stockmanagement.repository.InventoryRepository;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.repository.OrderRepository;
import com.example.stockmanagement.service.OrderService;
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

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(100.0);
        item.setStock(50);

        Orders order = new Orders();
        order.setId(1L);
        order.setQuantity(2);
        order.setPrice(200.0);
        order.setItems(Arrays.asList(item));

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(orderRepository.save(order)).thenReturn(order);
        when(itemRepository.save(item)).thenReturn(item);

        Orders result = orderService.saveOrder(order);
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testGetOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        Orders order = new Orders();
        order.setId(1L);
        order.setQuantity(2);
        order.setPrice(200.0);

        Page<Orders> page = new PageImpl<>(Arrays.asList(order));
        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<Orders> result = orderService.getOrders(pageable);
        assertEquals(1, result.getTotalElements());
        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetOrder() {
        Orders order = new Orders();
        order.setId(1L);
        order.setQuantity(2);
        order.setPrice(200.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders result = orderService.getOrder(1L);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteOrder() {
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateOrder() {
        Orders order = new Orders();
        order.setId(1L);
        order.setQuantity(2);
        order.setPrice(200.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Orders updatedOrder = new Orders();
        updatedOrder.setQuantity(5);
        updatedOrder.setPrice(500.0);

        Orders result = orderService.updateOrder(1L, updatedOrder);
        assertEquals(5, result.getQuantity());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }
}