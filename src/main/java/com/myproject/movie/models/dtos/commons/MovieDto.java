package com.myproject.movie.models.dtos.commons;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Positive(message = "Duration must be positive")
    private Long duration;

    @PastOrPresent(message = "Release date must be in the past or present")
    private LocalDateTime releaseDate;

    private String director;
    private String actors;

    @NotBlank(message = "Language is required")
    private String language;

    private String subtitle;
    private String rating;

    @NotBlank(message = "Poster URL is required")
    private String posterUrl;

    private String trailerUrl;

    @NotEmpty(message = "Categories cannot be empty")
    private List<CategoryDto> categories;
}
