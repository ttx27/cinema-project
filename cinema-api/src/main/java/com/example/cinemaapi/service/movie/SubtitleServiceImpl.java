package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.movie.Subtitle;
import com.example.cinemaapi.repository.movie.DirectorRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import com.example.cinemaapi.repository.movie.SubtitleRepository;
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
import static com.example.cinemaapi.exception.EntityType.SUBTITLE;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class SubtitleServiceImpl implements SubtitleService {
    @Autowired
    private SubtitleRepository subtitleRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Subtitle> getSubtitleByIds(List<Long> ids) {
        List<Subtitle> subtitles = new ArrayList<>();
        for(Subtitle subtitle : subtitleRepository.findAll())
        {
            if(ids.contains(subtitle.getId())){
                subtitles.add(subtitle);
            }
        }
        return subtitles;
    }

    @Override
    public Set<SubtitleDto> getAllSubtitles() {
        return StreamSupport
                .stream(subtitleRepository.findAll().spliterator(), false)
                .map(subtitle -> modelMapper.map(subtitle, SubtitleDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public SubtitleDto addSubtitle(SubtitleDto subtitleDto, UserDto userDto) {
        Subtitle subtitleModel = new Subtitle().setSubtitle(subtitleDto.getSubtitle());

        subtitleModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        subtitleModel.setCreatedBy(userDto.getId());

        subtitleRepository.save(subtitleModel);
        return modelMapper.map(subtitleModel, SubtitleDto.class);
    }

    @Transactional
    public SubtitleDto updateSubtitle(SubtitleDto subtitleDto, UserDto userDto) {
        Optional<Subtitle> subtitle = subtitleRepository.findById(subtitleDto.getId());
        if (subtitle != null) {
            Subtitle subtitleModel = subtitle.get();
            subtitleModel.setSubtitle(subtitleDto.getSubtitle());

            subtitleModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            subtitleModel.setModifiedBy(userDto.getId());

            return modelMapper.map(subtitleRepository.save(subtitleModel), SubtitleDto.class);
        }
        throw exceptionWithId(SUBTITLE, ENTITY_NOT_FOUND, 2, subtitleDto.getId().toString());
    }

    @Transactional
    public void deleteSubtitle(Long subtitleID) {
        Optional<Subtitle> subtitle = subtitleRepository.findById(subtitleID);
        if (subtitle != null && subtitle.isPresent()) {
            Subtitle subtitleModel = subtitle.get();

            Iterable<Movie> movies = movieRepository.findAll();

            for (Movie movie: movies) {
                movie.getSubtitles().removeIf(n -> n.getId() == subtitleModel.getId());
                movieRepository.save(movie);
            }

            subtitleRepository.deleteById(subtitleID);
        } else {
            throw exceptionWithId(SUBTITLE, ENTITY_NOT_FOUND, 2, subtitleID.toString());
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
