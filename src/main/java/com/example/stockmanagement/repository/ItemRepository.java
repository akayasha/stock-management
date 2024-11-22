package com.example.stockmanagement.repository;

import com.example.stockmanagement.entity.Item;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(@NotBlank String name);

    Optional<Item> getItemById(Long itemId);
}