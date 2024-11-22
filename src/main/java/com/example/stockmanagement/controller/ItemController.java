package com.example.stockmanagement.controller;

import com.example.stockmanagement.dto.ItemDTO;
import com.example.stockmanagement.dto.ResponseHandler;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<?> getAllItems(@PageableDefault(size = 10) Pageable pageable) {
        try {
            List<ItemDTO> items = itemService.getItems(pageable).stream()
                    .map(this::convertToItemDTO)
                    .collect(Collectors.toList());
            return ResponseHandler.successResponse(items);
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody @Valid ItemDTO itemDTO) {
        try {
            if (itemRepository.findByName(itemDTO.getName()).isPresent()) {
                return ResponseHandler.badRequestResponse("Item already exists");
            }
            Item item = convertToItem(itemDTO);
            Item savedItem = itemService.saveItem(item);
            return ResponseHandler.successResponse(convertToItemDTO(savedItem));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        try {
            if (!itemService.existsById(id)) {
                return ResponseHandler.badRequestResponse("Item not found");
            }
            Item existingItem = itemService.getItem(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));

            if (itemDTO.getName() != null) existingItem.setName(itemDTO.getName());
            if (itemDTO.getPrice() != null) existingItem.setPrice(itemDTO.getPrice());
            if (itemDTO.getStock() != null) existingItem.setStock(itemDTO.getStock());

            Item updatedItem = itemService.updateItem(id, existingItem);
            return ResponseHandler.successResponse(convertToItemDTO(updatedItem));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            if (!itemService.existsById(id)) {
                return ResponseHandler.badRequestResponse("Item not found");
            }
            itemService.deleteItem(id);
            return ResponseHandler.successResponse("Item deleted successfully");
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        try {
            Item item = itemService.getItem(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));
            return ResponseHandler.successResponse(convertToItemDTO(item));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    private ItemDTO convertToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setStock(item.getStock());
        return itemDTO;
    }

    private Item convertToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setStock(itemDTO.getStock());
        return item;
    }
}