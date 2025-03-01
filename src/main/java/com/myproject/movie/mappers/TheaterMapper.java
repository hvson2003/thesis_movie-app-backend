package com.myproject.movie.mappers;

import com.myproject.movie.models.entities.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TheaterMapper {
    void updateTheaterFromDto(Theater dto, @MappingTarget Theater theater);
}
