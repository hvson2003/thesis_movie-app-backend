package com.myproject.movie_booking.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private Integer duration;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseDate;

    private String director;
    private String actors;
    private String language;
    private String subtitle;
    private String rating;

    @JsonProperty("poster_url")
    private String posterUrl;

    @JsonProperty("trailer_url")
    private String trailerUrl;

    private List<CategoryDTO> categories;
}