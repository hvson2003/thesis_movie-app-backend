package com.myproject.movie.mappers;

import com.myproject.movie.dtos.commons.MovieDTO;
import com.myproject.movie.models.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MovieMapper {
    MovieDTO toDTO(Movie movie);
    Movie toEntity(MovieDTO movieDTO);
}
