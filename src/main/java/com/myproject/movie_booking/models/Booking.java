package com.myproject.movie_booking.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    private LocalDateTime bookingTime = LocalDateTime.now();
    private String status = "pending";
    private Float totalAmount;
    private Integer pointsUsed = 0;
    private Integer pointsEarned = 0;

    @OneToMany(mappedBy = "booking")
    private List<BookingSeat> bookingSeats;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;
}
