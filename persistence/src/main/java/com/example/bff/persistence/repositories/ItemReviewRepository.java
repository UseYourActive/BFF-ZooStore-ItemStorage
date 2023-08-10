package com.example.bff.persistence.repositories;

import com.example.bff.persistence.entities.ItemReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemReviewRepository extends JpaRepository<ItemReview, UUID> {
}
