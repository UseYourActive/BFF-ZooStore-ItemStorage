package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Builder
    public User(String email, String password, String firstName, String lastName, String phoneNumber, Timestamp registeredOn, ShoppingCart shoppingCart) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registeredOn = registeredOn;
        this.shoppingCart = shoppingCart;
        counter++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private static Integer counter;

    @CreationTimestamp
    private Timestamp registeredOn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private ShoppingCart shoppingCart;

    public boolean isWinner(){
        return counter % 100 == 0;
    }
}