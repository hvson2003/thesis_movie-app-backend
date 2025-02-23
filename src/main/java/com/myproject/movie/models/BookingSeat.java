package com.myproject.movie.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "booking_seats")
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "screening_seat_id", nullable = false)
    private ScreeningSeat screeningSeat;

    private Float price;
}
