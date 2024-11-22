package com.example.stockmanagement.service;

import com.example.stockmanagement.entity.Inventory;
import com.example.stockmanagement.entity.Orders;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.InventoryRepository;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Orders saveOrder(Orders orders) {
        List<Item> items = orders.getItems();

        for (Item item : items) {
            Inventory inventory = new Inventory();
            inventory.setItem(item);
            inventory.setType("W");
            inventory.setQuantity(orders.getQuantity());
            inventoryRepository.save(inventory);
            item.setStock(item.getStock() - orders.getQuantity());
            itemRepository.save(item);
        }
        return orderRepository.save(orders);
    }

    public Page<Orders> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Orders getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    public void updateOrder(Orders orders) {
        orderRepository.save(orders);
    }

    public Orders updateOrder(Long id, Orders orders) {
        Orders existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder == null) {
            return null;
        }
        existingOrder.setQuantity(orders.getQuantity());
        existingOrder.setItems(orders.getItems());
        return orderRepository.save(existingOrder);
    }
}