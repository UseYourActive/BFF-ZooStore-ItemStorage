package com.example.bff.persistence.repositories;

import com.example.bff.persistence.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvalidatedTokensRepository extends JpaRepository<Token, UUID> {
    Boolean existsByToken(String token);
}
