package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.MovieRequest;
import com.example.cinemaapi.dto.mapper.UserMapper;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.movie.*;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.service.movie.*;
import com.example.cinemaapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private SubtitleService subtitleService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllMovies(Principal principal) {
        Boolean isAdmin = false;

        if (principal != null) {
            String currentPrincipalName = principal.getName();
            User user = userRepository.findByEmail(currentPrincipalName);
            UserDto userDto = UserMapper.toUserDto(user);
            isAdmin = userDto.isAdmin();
        }

        return Response
                .ok().setPayload(movieService.getAllMovies(isAdmin));
    }

    @GetMapping("/{id}")
    public Response getMovieById(@PathVariable Long id) {
        return Response
                .ok().setPayload(movieService.getMovieById(id));
    }

    @PostMapping("")
    public Response createMovie(Principal principal, @RequestBody @Valid MovieRequest movieRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieDto movieDto = new MovieDto()
                .setName(movieRequest.getName())
                .setDescription(movieRequest.getDescription())
                .setImage(movieRequest.getImage())
                .setReleaseDate(movieRequest.getReleaseDate())
                .setDuration(movieRequest.getDuration())
                .setRating(movieRequest.getRating())
                .setStatus(movieRequest.getStatus());

            if (movieRequest.getActors() != null && !movieRequest.getActors().isEmpty()) {
                List<Actor> actors = actorService.getActorByIds(movieRequest.getActors());
                if (actors != null && !actors.isEmpty()) {
                    movieDto.setActors(actors);
                }
            }

            if (movieRequest.getCategories() != null && !movieRequest.getCategories().isEmpty()) {
                List<Category> categories = categoryService.getCategoryByIds(movieRequest.getCategories());
                if (categories != null && !categories.isEmpty()) {
                    movieDto.setCategories(categories);
                }
            }

            if (movieRequest.getDirectors() != null && !movieRequest.getDirectors().isEmpty()) {
                List<Director> directors = directorService.getDirectorByIds(movieRequest.getDirectors());
                if (directors != null && !directors.isEmpty()) {
                    movieDto.setDirectors(directors);
                }
            }

            if (movieRequest.getSubtitles() != null && !movieRequest.getSubtitles().isEmpty()) {
                List<Subtitle> subtitles = subtitleService.getSubtitleByIds(movieRequest.getSubtitles());
                if (subtitles != null && !subtitles.isEmpty()) {
                    movieDto.setSubtitles(subtitles);
                }
            }

            if (movieRequest.getLanguages() != null && !movieRequest.getLanguages().isEmpty()) {
                List<Language> languages = languageService.getLanguageByIds(movieRequest.getLanguages());
                if (languages != null && !languages.isEmpty()) {
                    movieDto.setLanguages(languages);
                }
            }

            return Response
                .ok().setPayload(movieService.addMovie(movieDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateMovie(Principal principal, @PathVariable Long id, @RequestBody @Valid MovieRequest movieRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieDto movieDto = new MovieDto()
            .setId(id)
            .setName(movieRequest.getName())
            .setDescription(movieRequest.getDescription())
            .setImage(movieRequest.getImage())
            .setReleaseDate(movieRequest.getReleaseDate())
            .setDuration(movieRequest.getDuration())
            .setRating(movieRequest.getRating())
            .setStatus(movieRequest.getStatus());

        if (movieRequest.getActors() != null && !movieRequest.getActors().isEmpty()) {
            List<Actor> actors = actorService.getActorByIds(movieRequest.getActors());
            if (actors != null && !actors.isEmpty()) {
                movieDto.setActors(actors);
            }
        }

        if (movieRequest.getCategories() != null && !movieRequest.getCategories().isEmpty()) {
            List<Category> categories = categoryService.getCategoryByIds(movieRequest.getCategories());
            if (categories != null && !categories.isEmpty()) {
                movieDto.setCategories(categories);
            }
        }

        if (movieRequest.getDirectors() != null && !movieRequest.getDirectors().isEmpty()) {
            List<Director> directors = directorService.getDirectorByIds(movieRequest.getDirectors());
            if (directors != null && !directors.isEmpty()) {
                movieDto.setDirectors(directors);
            }
        }

        if (movieRequest.getSubtitles() != null && !movieRequest.getSubtitles().isEmpty()) {
            List<Subtitle> subtitles = subtitleService.getSubtitleByIds(movieRequest.getSubtitles());
            if (subtitles != null && !subtitles.isEmpty()) {
                movieDto.setSubtitles(subtitles);
            }
        }

        if (movieRequest.getLanguages() != null && !movieRequest.getLanguages().isEmpty()) {
            List<Language> languages = languageService.getLanguageByIds(movieRequest.getLanguages());
            if (languages != null && !languages.isEmpty()) {
                movieDto.setLanguages(languages);
            }
        }

        return Response
                .ok().setPayload(movieService.updateMovie(movieDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return Response
                .ok();
    }
}
