package com.myproject.movie.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "row")
    private String row;

    @Column(name = "seat_type", nullable = false)
    private String seatType = "standard";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}