package com.myproject.movie_booking.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String phone;
    private String description;
    private Boolean isActive = true;

    @OneToMany(mappedBy = "theater")
    private List<Room> rooms;
}