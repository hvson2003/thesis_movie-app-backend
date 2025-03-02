package com.myproject.movie.mappers;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.entities.ScreeningSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatScreeningMapper {
    @Mapping(source = "seat.row", target = "row")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    @Mapping(source = "seat.seatType", target = "seatType")
    ScreeningSeatDto seatToSeatDto(ScreeningSeat screeningSeat);
}
