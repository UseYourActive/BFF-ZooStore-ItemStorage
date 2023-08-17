package com.example.bff.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bff.persistence.entities.ShoppingCart;

import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID>{
}
