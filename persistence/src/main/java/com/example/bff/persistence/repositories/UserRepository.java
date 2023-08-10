package com.example.bff.persistence.repositories;

import com.example.bff.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
