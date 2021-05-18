package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Language;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.movie.DirectorRepository;
import com.example.cinemaapi.repository.movie.LanguageRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.DIRECTOR;
import static com.example.cinemaapi.exception.EntityType.LANGUAGE;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Language> getLanguageByIds(List<Long> ids) {
        List<Language> languages = new ArrayList<>();
        for(Language language : languageRepository.findAll())
        {
            if(ids.contains(language.getId())){
                languages.add(language);
            }
        }
        return languages;
    }

    @Override
    public Set<LanguageDto> getAllLanguages() {
        return StreamSupport
                .stream(languageRepository.findAll().spliterator(), false)
                .map(language -> modelMapper.map(language, LanguageDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public LanguageDto addLanguage(LanguageDto languageDto, UserDto userDto) {
        Language languageModel = new Language().setLanguage(languageDto.getLanguage());

        languageModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        languageModel.setCreatedBy(userDto.getId());

        languageRepository.save(languageModel);
        return modelMapper.map(languageModel, LanguageDto.class);
    }

    @Transactional
    public LanguageDto updateLanguage(LanguageDto languageDto, UserDto userDto) {
        Optional<Language> language = languageRepository.findById(languageDto.getId());
        if (language != null) {
            Language languageModel = language.get();
            languageModel.setLanguage(languageDto.getLanguage());

            languageModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            languageModel.setModifiedBy(userDto.getId());

            return modelMapper.map(languageRepository.save(languageModel), LanguageDto.class);
        }
        throw exceptionWithId(LANGUAGE, ENTITY_NOT_FOUND, 2, languageDto.getId().toString());
    }

    @Transactional
    public void deleteLanguage(Long languageID) {
        Optional<Language> language = languageRepository.findById(languageID);
        if (language != null && language.isPresent()) {
            Language languageModel = language.get();

            Iterable<Movie> movies = movieRepository.findAll();

            for (Movie movie: movies) {
                movie.getLanguages().removeIf(n -> n.getId() == languageModel.getId());
                movieRepository.save(movie);
            }

            languageRepository.deleteById(languageID);
        } else {
            throw exceptionWithId(LANGUAGE, ENTITY_NOT_FOUND, 2, languageID.toString());
        }
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BRSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
