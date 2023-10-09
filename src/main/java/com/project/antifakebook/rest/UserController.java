package com.project.antifakebook.rest;

import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.RegisterAccountRequestDto;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.service.JwtService;
import com.project.antifakebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestParam String email,@RequestParam String password)  {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(email);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    @PostMapping("/sign-up")
    public ServerResponseDto registerAccount(@RequestBody RegisterAccountRequestDto requestDto) {
        return userService.registerAccount(requestDto);
    }
}
