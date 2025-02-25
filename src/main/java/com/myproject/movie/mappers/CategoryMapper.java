package com.myproject.movie.mappers;

import com.myproject.movie.models.dtos.commons.CategoryDto;
import com.myproject.movie.models.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDTO(Category category);
    Category toEntity(CategoryDto categoryDTO);
}
