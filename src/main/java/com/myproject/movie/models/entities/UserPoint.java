package com.myproject.movie.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_points")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pointId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer points;
    private String type;
    private LocalDateTime earnedDate = LocalDateTime.now();
    private LocalDateTime expiryDate;
}

