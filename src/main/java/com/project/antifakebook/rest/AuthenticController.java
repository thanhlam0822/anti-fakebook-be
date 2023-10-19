package com.project.antifakebook.rest;

import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.AccountLoginRequestDto;
import com.project.antifakebook.dto.account.RegisterAccountRequestDto;

import com.project.antifakebook.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class AuthenticController {
    private final UserService userService;

    public AuthenticController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ServerResponseDto authenticateAndGetToken(@RequestBody AccountLoginRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @PostMapping("/sign-up")
    public ServerResponseDto registerAccount(@RequestBody RegisterAccountRequestDto requestDto, HttpServletRequest servletRequest) {
        return userService.registerAccount(requestDto,servletRequest);
    }

}
