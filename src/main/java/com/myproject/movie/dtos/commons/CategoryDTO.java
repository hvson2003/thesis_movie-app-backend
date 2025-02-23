package com.myproject.movie.dtos.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    @JsonProperty("category_id")
    private Integer id;
    private String name;
}
