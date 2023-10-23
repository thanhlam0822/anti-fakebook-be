package com.project.antifakebook.rest;

import com.project.antifakebook.dto.ServerResponseDto;

import com.project.antifakebook.service.VerifyTokenService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RequestMapping("/api/verify-token")
@RestController
public class VerifyTokenController {
    private final VerifyTokenService service;
    public VerifyTokenController(VerifyTokenService service) {
        this.service = service;
    }
    @GetMapping("/get-verify-code")
    public ServerResponseDto getVerifyCode(@RequestParam String email,HttpServletRequest servletRequest) throws ParseException {
        return service.getVerifyTokenService(email, servletRequest);
    }
    @GetMapping("/check-verify-code")
    public ServerResponseDto confirmRegistration(@RequestParam String token, @RequestParam String email) throws ParseException {
        return service.checkVerifyCode(token, email);
    }
}
