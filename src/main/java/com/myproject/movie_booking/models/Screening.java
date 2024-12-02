package com.myproject.movie_booking.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float price;
    private String subtitleType;
    private Boolean isActive = true;
//
//    @OneToMany(mappedBy = "screening")
//    private List<Booking> bookings;
//
//    @OneToMany(mappedBy = "screening")
//    private List<ScreeningSeat> screeningSeats;
}

