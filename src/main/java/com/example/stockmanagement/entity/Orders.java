package com.example.stockmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany
        @JoinColumn(name = "order_id")
        private List<Item> items;

        @Positive
        @Column(nullable = false)
        private Integer quantity;

        @Positive
        @Column(nullable = false)
        private Double price;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(nullable = false)
        private Date date;

        public List<Item> getItems() {
                return items;
        }

        public void setItems(List<Item> items) {
                this.items = items;
        }
}