package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.SubtitleRequest;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.movie.SubtitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/subtitles")
public class SubtitleController {
    @Autowired
    private SubtitleService subtitleService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllSubtitles() {
        return Response
                .ok().setPayload(subtitleService.getAllSubtitles());
    }

    @PostMapping("")
    public Response createSubtitle(Principal principal, @RequestBody @Valid SubtitleRequest subtitleRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SubtitleDto subtitleDto = new SubtitleDto()
                    .setSubtitle(subtitleRequest.getSubtitle());

            return Response
                .ok().setPayload(subtitleService.addSubtitle(subtitleDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateSubtitle(Principal principal, @PathVariable Long id, @RequestBody @Valid SubtitleRequest subtitleRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SubtitleDto subtitleDto = new SubtitleDto()
            .setId(id)
            .setSubtitle(subtitleRequest.getSubtitle());

        return Response
                .ok().setPayload(subtitleService.updateSubtitle(subtitleDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteSubtitle(@PathVariable Long id) {
        subtitleService.deleteSubtitle(id);
        return Response.ok();
    }
}
