package com.myproject.movie_booking.dtos.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    @JsonProperty("category_id")
    private Integer categoryId;
}
