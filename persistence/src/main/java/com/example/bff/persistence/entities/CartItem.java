package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Builder
    public CartItem(UUID targetItemId, Integer quantity, BigDecimal price, List<ItemReview> reviews) {
        this.targetItemId = targetItemId;
        this.quantity = quantity;
        this.price = price;
        this.reviews = reviews;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //@JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private UUID targetItemId;

    private Integer quantity;

    private BigDecimal price;

    @OneToMany
    private List<ItemReview> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(targetItemId, cartItem.targetItemId);
    }

    @Override
    public int hashCode() {
        return targetItemId.hashCode();
    }
}
