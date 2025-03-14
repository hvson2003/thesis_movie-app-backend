package com.myproject.movie.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
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

