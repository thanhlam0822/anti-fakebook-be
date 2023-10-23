package com.project.antifakebook.rest;


import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("change-info-after-signup")
    public ServerResponseDto changeInfoAfterSignUp(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("username") String username,
                                                   Authentication authentication) throws IOException {


        return userService.changeInfoAfterSignUp(authentication,username,file);
    }

}
