package com.myproject.movie.models.entities;

import com.myproject.movie.models.enums.BookingStatus;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(nullable = false)
    private Float totalAmount;

    @Column(nullable = false)
    private Long pointsUsed = 0L;

    @Column(nullable = false)
    private Long pointsEarned = 0L;

    @OneToMany(mappedBy = "booking")
    private List<BookingSeat> bookingSeats;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @PrePersist
    public void prePersist() {
        this.bookingTime = LocalDateTime.now();
    }
}
