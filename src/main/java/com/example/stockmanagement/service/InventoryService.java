package com.example.stockmanagement.service;

import com.example.stockmanagement.entity.Inventory;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.InventoryRepository;
import com.example.stockmanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Inventory saveInventory(Inventory inventory) {
        Item item = inventory.getItem();
        item = itemRepository.findById(item.getId()).orElseThrow(() -> new IllegalArgumentException("Item not found"));
        if (inventory.getType().equalsIgnoreCase("T")) {
            item.setStock(item.getStock() + inventory.getQuantity());
        } else if (inventory.getType().equalsIgnoreCase("W")) {
            if (item.getStock() < inventory.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock");
            }
            item.setStock(item.getStock() - inventory.getQuantity());
        } else {
            throw new IllegalArgumentException("Invalid inventory type");
        }
        itemRepository.save(item);
        return inventoryRepository.save(inventory);
    }

    public Page<Inventory> getInventories(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Inventory getInventory(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return inventoryRepository.existsById(id);
    }

    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findById(id).orElse(null);
        if (existingInventory == null) {
            return null;
        }
        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setType(inventory.getType());
        existingInventory.setItem(inventory.getItem());
        return inventoryRepository.save(existingInventory);
    }
}