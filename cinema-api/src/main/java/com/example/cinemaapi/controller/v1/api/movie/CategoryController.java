package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.CategoryRequest;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.movie.CategoryService;
import com.example.cinemaapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
    @RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllCategories() {
        return Response
                .ok().setPayload(categoryService.getAllCategories());
    }

    @PostMapping("")
    public Response createCategory(Principal principal, @RequestBody @Valid CategoryRequest categoryRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        CategoryDto categoryDto = new CategoryDto()
                .setName(categoryRequest.getName())
                .setCode(categoryRequest.getCode())
                .setStatus(1);

            return Response
                .ok().setPayload(categoryService.addCategory(categoryDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateCategory(Principal principal, @PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        CategoryDto categoryDto = new CategoryDto()
            .setId(id)
            .setName(categoryRequest.getName())
            .setCode(categoryRequest.getCode())
            .setStatus(1);

        return Response
                .ok().setPayload(categoryService.updateCategory(categoryDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Response.ok();
    }
}
