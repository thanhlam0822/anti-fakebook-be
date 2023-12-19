package com.project.antifakebook.rest;

import com.project.antifakebook.dto.ServerResponseDto;

import com.project.antifakebook.dto.verify_code.CheckVerifyCodeRequestDto;
import com.project.antifakebook.dto.verify_code.GetVerifyCodeRequestDto;
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
    @PostMapping ("/get-verify-code")
    public ServerResponseDto getVerifyCode(@RequestBody GetVerifyCodeRequestDto requestDto, HttpServletRequest servletRequest) throws ParseException {
        return service.getVerifyTokenService(requestDto, servletRequest);
    }
    @PostMapping("/check-verify-code")
    public ServerResponseDto confirmRegistration(@RequestBody CheckVerifyCodeRequestDto requestDto) throws ParseException {
        return service.checkVerifyCode(requestDto);
    }
}
