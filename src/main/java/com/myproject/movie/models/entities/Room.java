package com.myproject.movie.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    @JsonIgnore
    private Theater theater;

    @Column(nullable = false)
    private String name;

    private Long capacity;
    private String roomType;
    private Boolean isActive = true;

//    @OneToMany(mappedBy = "room")
//    private List<Seat> seats;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Screening> screenings;
}
