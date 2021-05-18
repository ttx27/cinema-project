package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Subtitle;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface SubtitleService {
    List<Subtitle> getSubtitleByIds(List<Long> ids);

    Set<SubtitleDto> getAllSubtitles();

    SubtitleDto addSubtitle(SubtitleDto subtitleDto, UserDto userDto);

    SubtitleDto updateSubtitle(SubtitleDto subtitleDto, UserDto userDto);

    void deleteSubtitle(Long subtitleID);
}
