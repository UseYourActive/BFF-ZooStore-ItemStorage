package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @OneToMany()
//    @JoinColumn(name = "user_id")
    private Set<CartItem> cartItems;

    public void addItemToCart(CartItem cartItem){
        this.cartItems.add(cartItem);
    }

    public void removeItemFromCart(CartItem cartItem){
        this.cartItems.remove(cartItem);
    }

    public void removeItemFromCart(UUID cartItemId) {
        this.cartItems.removeIf(cartItem -> cartItem.getTargetItem().equals(cartItemId));
    }
}