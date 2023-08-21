package com.example.bff.persistence.repositories;

import com.example.bff.persistence.entities.EndowmentFoundation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EndowmentFoundationRepository extends JpaRepository<EndowmentFoundation, UUID> {
}
