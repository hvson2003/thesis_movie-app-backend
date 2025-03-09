package com.myproject.movie.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String logo;
    private String description;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Theater> theaters;
}
