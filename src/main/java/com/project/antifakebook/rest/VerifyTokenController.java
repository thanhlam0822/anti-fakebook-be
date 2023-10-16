package com.project.antifakebook.rest;

import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.repository.VerifyTokenRepository;
import com.project.antifakebook.service.VerifyTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class VerifyTokenController {
    private final VerifyTokenService service;
    public VerifyTokenController(VerifyTokenService service) {
        this.service = service;
    }
    @GetMapping("/get-verify-code")
    public ServerResponseDto getVerifyCode(@RequestParam String email) {
        return service.getVerifyToken(email);
    }
}
