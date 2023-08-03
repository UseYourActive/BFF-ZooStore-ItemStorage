package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Builder
    public CartItem(UUID referencedItemId, Integer quantity, BigDecimal price, User user) {
        this.targetItem = referencedItemId;
        this.quantity = quantity;
        this.price = price;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID targetItem;

    private Integer quantity;

    private BigDecimal price;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(targetItem, cartItem.targetItem);
    }

    @Override
    public int hashCode() {
        return targetItem.hashCode();
    }
}
