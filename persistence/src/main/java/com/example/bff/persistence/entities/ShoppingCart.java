package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Builder
    public ShoppingCart(UUID userId, List<CartItem> items, Integer quantity, BigDecimal totalPrice) {
        this.userId = userId;
        this.items = items;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        itemsInCart++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //@Column(nullable = false)
    private UUID userId;

    @OneToMany
    private List<CartItem> items;

    //@Column(nullable = false)
    private Integer quantity;

    //@Column(nullable = false)
    private BigDecimal totalPrice;

    private static Integer itemsInCart;
}
