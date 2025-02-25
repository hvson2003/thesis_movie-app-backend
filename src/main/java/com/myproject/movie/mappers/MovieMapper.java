package com.myproject.movie.mappers;

import com.myproject.movie.models.dtos.commons.MovieDto;
import com.myproject.movie.models.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MovieMapper {
    MovieDto toDTO(Movie movie);
    Movie toEntity(MovieDto movieDTO);
}
