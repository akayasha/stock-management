package com.example.stockmanagement.controller;

import com.example.stockmanagement.dto.ItemDTO;
import com.example.stockmanagement.dto.OrderDTO;
import com.example.stockmanagement.dto.ResponseHandler;
import com.example.stockmanagement.entity.Item;
import com.example.stockmanagement.entity.Orders;
import com.example.stockmanagement.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(Pageable pageable) {
        try {
            List<OrderDTO> orders = orderService.getOrders(pageable).stream()
                    .map(this::convertToOrderDTO)
                    .collect(Collectors.toList());
            return ResponseHandler.successResponse(orders);
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse("An error occurred while fetching orders");
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            Orders order = convertToOrder(orderDTO);
            Orders savedOrder = orderService.saveOrder(order);
            return ResponseHandler.successResponse(convertToOrderDTO(savedOrder));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse("An error occurred while saving order");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderDTO orderDTO) {
        try {
            Orders existingOrder = orderService.getOrder(id);
            if (existingOrder == null) {
                return ResponseHandler.badRequestResponse("Order not found");
            }
            if (orderDTO.getQuantity() != null) existingOrder.setQuantity(orderDTO.getQuantity());
            if (orderDTO.getPrice() != null) existingOrder.setPrice(orderDTO.getPrice());
            if (orderDTO.getDate() != null) existingOrder.setDate(orderDTO.getDate());
            if (orderDTO.getItems() != null) existingOrder.setItems(orderDTO.getItems().stream()
                    .map(this::convertToItem)
                    .collect(Collectors.toList()));
            Orders updatedOrder = orderService.saveOrder(existingOrder);
            return ResponseHandler.successResponse(convertToOrderDTO(updatedOrder));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse("An error occurred while updating order");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            Orders order = orderService.getOrder(id);
            return ResponseHandler.successResponse(convertToOrderDTO(order));
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse("An error occurred while fetching order");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            if (!orderService.existsById(id)) {
                return ResponseHandler.badRequestResponse("Order not found");
            }
            orderService.deleteOrder(id);
            return ResponseHandler.successResponse("Order deleted successfully");
        } catch (Exception e) {
            return ResponseHandler.badRequestResponse("An error occurred while deleting order");
        }
    }

    private OrderDTO convertToOrderDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setDate(order.getDate());
        orderDTO.setItems(order.getItems().stream()
                .map(this::convertToItemDTO)
                .collect(Collectors.toList()));
        return orderDTO;
    }

    private Orders convertToOrder(OrderDTO orderDTO) {
        Orders order = new Orders();
        order.setId(orderDTO.getId());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setDate(orderDTO.getDate());
        order.setItems(orderDTO.getItems().stream()
                .map(this::convertToItem)
                .collect(Collectors.toList()));
        return order;
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