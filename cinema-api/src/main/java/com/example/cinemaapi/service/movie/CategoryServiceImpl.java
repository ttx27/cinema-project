package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.movie.ActorRepository;
import com.example.cinemaapi.repository.movie.CategoryRepository;
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

import static com.example.cinemaapi.exception.EntityType.ACTOR;
import static com.example.cinemaapi.exception.EntityType.CATEGORY;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Category> getCategoryByIds(List<Long> ids) {
        List<Category> categories = new ArrayList<>();
        for(Category category : categoryRepository.findAll())
        {
            if(ids.contains(category.getId())){
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public Set<CategoryDto> getAllCategories() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto, UserDto userDto) {
        Category categoryModel = new Category()
                .setName(categoryDto.getName())
                .setCode(categoryDto.getCode())
                .setStatus(categoryDto.getStatus());

        categoryModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        categoryModel.setCreatedBy(userDto.getId());

        categoryRepository.save(categoryModel);
        return modelMapper.map(categoryModel, CategoryDto.class);
    }

    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto, UserDto userDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getId());
        if (category != null) {
            Category categoryModel = category.get();
            categoryModel
                .setName(categoryDto.getName())
                .setCode(categoryDto.getCode())
                .setStatus(categoryDto.getStatus());

            categoryModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            categoryModel.setModifiedBy(userDto.getId());

            return modelMapper.map(categoryRepository.save(categoryModel), CategoryDto.class);
        }
        throw exceptionWithId(CATEGORY, ENTITY_NOT_FOUND, 2, categoryDto.getId().toString());
    }

    @Transactional
    public void deleteCategory(Long categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category != null && category.isPresent()) {
            Category categoryModel = category.get();

            Iterable<Movie> movies = movieRepository.findAll();

            for (Movie movie: movies) {
                movie.getCategories().removeIf(n -> n.getId() == categoryModel.getId());
                movieRepository.save(movie);
            }

            categoryRepository.deleteById(categoryID);
        } else {
            throw exceptionWithId(CATEGORY, ENTITY_NOT_FOUND, 2, categoryID.toString());
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
