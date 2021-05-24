package com.example.cinemaapi.controller.v1.api;

import com.example.cinemaapi.controller.v1.request.UserResetPasswordRequest;
import com.example.cinemaapi.controller.v1.request.UserSigninRequest;
import com.example.cinemaapi.controller.v1.request.UserSignupRequest;
import com.example.cinemaapi.controller.v1.request.UserVerifyRequest;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.EmailSenderService;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.util.FileUploadUtil;
import com.example.cinemaapi.util.RandomStringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/upload")
@Api(value = "cinema-application", description = "Operations pertaining to user management in the cinema application")
public class UploadController {
    @Autowired
    ResourceLoader resourceLoader;

    @Value("${upload.path}")
    private String fileUpload;

    @PostMapping("")
    public Response uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(this.fileUpload + "uploads/" +fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return Response.ok().setPayload("uploads/" + fileName);
    }
}
