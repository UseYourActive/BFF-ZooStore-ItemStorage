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
@Table(name = "item_comments")
public class ItemReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID itemId;
    private String comment;
    @ManyToOne
    //@JoinColumn(name = "user_id")
    private User user;
    private Integer rating;
}
