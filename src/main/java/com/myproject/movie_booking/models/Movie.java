package com.myproject.movie_booking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String posterUrl;
    private String trailerUrl;
    private Integer duration;
    private LocalDateTime releaseDate;
    private String director;
    private String actors;
    private String language;
    private String subtitle;
    private String rating;
    private Boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();

//    @OneToMany(mappedBy = "movie")
//    private List<Screening> screenings;
//
//    @OneToMany(mappedBy = "movie")
//    private List<Review> reviews;
//
    @OneToMany(mappedBy = "movie")
    @JsonManagedReference

    private List<MovieCategory> categories;
}
