package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

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

//    @Enumerated(EnumType.STRING)
//    private Role role;
}