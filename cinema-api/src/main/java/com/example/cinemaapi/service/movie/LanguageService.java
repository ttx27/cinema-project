package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Language;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface LanguageService {
    List<Language> getLanguageByIds(List<Long> ids);

    Set<LanguageDto> getAllLanguages();

    LanguageDto addLanguage(LanguageDto languageDto, UserDto userDto);

    LanguageDto updateLanguage(LanguageDto languageDto, UserDto userDto);

    void deleteLanguage(Long languageID);
}
