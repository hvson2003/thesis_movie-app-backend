package com.myproject.movie.mappers;

import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import com.myproject.movie.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User userCreateRequestDtoToEntity(UserCreateRequestDto userCreateRequestDto);
    UserCreateRequestDto entityToUserCreateRequestDto(User user);
    UserUpdateRequestDto entityToUserUpdateRequestDto(User user);
    UserReadResponseDto entityToUserReadResponseDto(User user);

    void updateUserFromDto(UserUpdateRequestDto dto, @MappingTarget User user);
}
