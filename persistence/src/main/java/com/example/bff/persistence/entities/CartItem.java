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
    public CartItem(UUID referencedItemId, Integer quantity, BigDecimal price, User user) {
        this.targetItem = referencedItemId;
        this.quantity = quantity;
        this.price = price;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //@JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private UUID targetItem;

    private Integer quantity;

    private BigDecimal price;

//    private UUID vendorId;
//
//    @ElementCollection
//    private Set<UUID> tagIds;
//
//    @ElementCollection
//    private Set<UUID> multimediaIds;
//
//    private String productName;
//
//    private String description;
//
//    private Boolean archived;

    @ManyToOne
    private User user;

    @OneToMany
    private List<ItemReview> reviews;

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
