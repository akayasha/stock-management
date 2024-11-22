package com.example.stockmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;


    @Positive
    @Column(nullable = false)
    private Double price;

    @Positive
    @Column(nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Inventory> inventories;

}