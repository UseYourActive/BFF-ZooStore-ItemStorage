package com.example.bff.persistence.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "invalidated_tokens")
@Getter
@NoArgsConstructor
public class Token {
    @Builder
    public Token(String token) {
        this.token = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;
}
