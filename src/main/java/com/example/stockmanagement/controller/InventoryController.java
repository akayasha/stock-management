package com.example.stockmanagement.controller;

import com.example.stockmanagement.dto.InventoryDTO;
import com.example.stockmanagement.dto.ItemDTO;
import com.example.stockmanagement.dto.ResponseHandler;
import com.example.stockmanagement.entity.Inventory;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.repository.ItemRepository;
import com.example.stockmanagement.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<?> saveInventory(@RequestBody @Valid InventoryDTO inventoryDTO) {
        try {
            Inventory inventory = convertToInventoryWithItemIdOnly(inventoryDTO);
            Inventory savedInventory = inventoryService.saveInventory(inventory);
            return ResponseHandler.successResponse(convertToInventoryDTO(savedInventory));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        try {
            Inventory existingInventory = inventoryService.getInventory(id);
            if (existingInventory == null) {
                return ResponseHandler.badRequestResponse("Inventory not found");
            }
            if (inventoryDTO.getQuantity() != null) existingInventory.setQuantity(inventoryDTO.getQuantity());
            if (inventoryDTO.getType() != null) existingInventory.setType(inventoryDTO.getType());
            if (inventoryDTO.getItem() != null) {
                Item item = new Item();
                item.setId(inventoryDTO.getItem().getId());
                existingInventory.setItem(item);
            }
            Inventory updatedInventory = inventoryService.saveInventory(existingInventory);
            return ResponseHandler.successResponse(convertToInventoryDTO(updatedInventory));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getInventories(Pageable pageable) {
        try {
            List<InventoryDTO> inventories = inventoryService.getInventories(pageable).stream()
                    .map(this::convertToInventoryDTO)
                    .collect(Collectors.toList());
            return ResponseHandler.successResponse(inventories);
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventory(@PathVariable Long id) {
        try {
            if (!inventoryService.existsById(id)) {
                return ResponseHandler.badRequestResponse("Inventory not found");
            }
            Inventory inventory = inventoryService.getInventory(id);
            return ResponseHandler.successResponse(convertToInventoryDTO(inventory));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        try {
            if (!inventoryService.existsById(id)) {
                return ResponseHandler.badRequestResponse("Inventory not found");
            }
            inventoryService.deleteInventory(id);
            return ResponseHandler.successResponse("Inventory deleted successfully");
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse(e.getMessage());
        }
    }

    private InventoryDTO convertToInventoryDTO(Inventory inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setQuantity(inventory.getQuantity());
        inventoryDTO.setType(inventory.getType());

        Item item = inventory.getItem();
        if (item != null) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setStock(item.getStock());
            inventoryDTO.setItem(itemDTO);
        }

        return inventoryDTO;
    }

    private Inventory convertToInventoryWithItemIdOnly(@Valid InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setType(inventoryDTO.getType());

        if (inventoryDTO.getItem() != null && inventoryDTO.getItem().getId() != null) {
            Long itemId = inventoryDTO.getItem().getId();

            // Fetch the item details from the database using the itemId
            Optional<Item> fetchedItem = itemRepository.getItemById(itemId); // Use your ItemService or repository here.

            if (fetchedItem.isPresent()) {
                Item item = fetchedItem.get();

                System.out.printf("Item id: %d\n", item.getId());
                System.out.printf("Item name: %s\n", item.getName());
                System.out.printf("Item price: %f\n", item.getPrice());
                System.out.printf("Item stock: %d\n", item.getStock());

                inventory.setItem(item);
            } else {
                System.out.println("No item found with id: " + itemId);
            }
        } else {
            System.out.println("Invalid or missing item details in InventoryDTO");
        }

        return inventory;
    }


}