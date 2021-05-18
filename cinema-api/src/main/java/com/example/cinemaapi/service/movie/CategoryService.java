package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Category;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface CategoryService {
    List<Category> getCategoryByIds(List<Long> ids);

    Set<CategoryDto> getAllCategories();

    CategoryDto addCategory(CategoryDto categoryDto, UserDto userDto);

    CategoryDto updateCategory(CategoryDto categoryDto, UserDto userDto);

    void deleteCategory(Long categoryID);
}
