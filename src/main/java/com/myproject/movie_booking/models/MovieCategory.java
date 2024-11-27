package com.myproject.movie_booking.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "movie_categories")
public class MovieCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
