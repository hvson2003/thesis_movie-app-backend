package com.myproject.movie.mappers;

import com.myproject.movie.dtos.commons.CategoryDTO;
import com.myproject.movie.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
