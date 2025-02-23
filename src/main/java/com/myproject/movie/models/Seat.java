package com.myproject.movie.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String seatNumber;

    private String row;
    private String seatType = "standard";
    private Boolean isActive = true;
//
//    @OneToMany(mappedBy = "seat")
//    private List<ScreeningSeat> screeningSeats;
}
