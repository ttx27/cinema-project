package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Director;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface DirectorService {
    List<Director> getDirectorByIds(List<Long> ids);

    Set<DirectorDto> getAllDirectors();

    DirectorDto addDirector(DirectorDto directorDto, UserDto userDto);

    DirectorDto updateDirector(DirectorDto directorDto, UserDto userDto);

    void deleteDirector(Long directorID);
}
