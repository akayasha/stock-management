package com.example.stockmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    @NotNull(message = "Items are mandatory")
    private List<ItemDTO> items;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Date is mandatory")
    private Date date;
}