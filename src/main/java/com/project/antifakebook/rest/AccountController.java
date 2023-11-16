package com.project.antifakebook.rest;


import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.ChangePasswordRequestDto;
import com.project.antifakebook.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("change-password")
    public ServerResponseDto changePassword(@AuthenticationPrincipal CustomUserDetails currentUser,
                                            @RequestBody ChangePasswordRequestDto requestDto) {
        return userService.changePassword(currentUser.getUserId(), currentUser.getName(),requestDto);
    }

}
