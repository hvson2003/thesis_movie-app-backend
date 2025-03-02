package com.myproject.movie.models.entities;

import com.myproject.movie.models.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "screening_seats")
@Data
public class ScreeningSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "screening_id", nullable = false)
    private Integer screeningId;

    @Column(name = "seat_id", nullable = false)
    private Integer seatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", insertable = false, updatable = false)
    private Seat seat;
}


