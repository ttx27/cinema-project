package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.LanguageRequest;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.service.movie.LanguageService;
import com.example.cinemaapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/languages")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllLanguages() {
        return Response
                .ok().setPayload(languageService.getAllLanguages());
    }

    @PostMapping("")
    public Response createLanguage(Principal principal, @RequestBody @Valid LanguageRequest languageRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        LanguageDto languageDto = new LanguageDto()
                .setLanguage(languageRequest.getLanguage());

            return Response
                .ok().setPayload(languageService.addLanguage(languageDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateLanguage(Principal principal, @PathVariable Long id, @RequestBody @Valid LanguageRequest languageRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        LanguageDto languageDto = new LanguageDto()
            .setId(id)
            .setLanguage(languageRequest.getLanguage());

        return Response
                .ok().setPayload(languageService.updateLanguage(languageDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguage(id);
        return Response
                .ok();
    }
}
