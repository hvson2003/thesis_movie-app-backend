package com.myproject.movie.models.dtos.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDto {
    @JsonProperty("category_id")
    private Integer id;
    private String name;
}
