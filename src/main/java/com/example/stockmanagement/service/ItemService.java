package com.example.stockmanagement.service;

import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.ItemRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> getItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> getItem(Long id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public Item updateItem(Long id, Item item) {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }
        existingItem.setName(item.getName());
        existingItem.setStock(item.getStock());
        return itemRepository.save(existingItem);
    }

}