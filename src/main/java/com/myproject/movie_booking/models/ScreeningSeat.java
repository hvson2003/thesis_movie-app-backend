package com.myproject.movie_booking.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "screening_seats")
public class ScreeningSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    private String status = "available";
    private Float price;

//    @OneToMany(mappedBy = "screeningSeat")
//    private List<BookingSeat> bookings;
}

