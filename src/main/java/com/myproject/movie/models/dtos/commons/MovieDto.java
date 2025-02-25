package com.myproject.movie.models.dtos.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {
    private Integer id;
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

    private String posterUrl;

    private String trailerUrl;

    private List<CategoryDto> categories;
}