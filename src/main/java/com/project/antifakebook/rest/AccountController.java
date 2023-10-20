package com.project.antifakebook.rest;


import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.ChangeInfoAfterSignUpRequestDto;
import com.project.antifakebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("change-info-after-signup")
    public ServerResponseDto changeInfoAfterSignUp(@RequestBody ChangeInfoAfterSignUpRequestDto requestDto,
                                                   Authentication authentication) {
        return userService.changeInfoAfterSignUp(authentication,requestDto);
    }

}
