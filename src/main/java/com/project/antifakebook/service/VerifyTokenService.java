package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.entity.VerificationTokenEntity;
import com.project.antifakebook.repository.VerifyTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class VerifyTokenService {
    private final VerifyTokenRepository repository;

    public VerifyTokenService(VerifyTokenRepository verifyTokenRepository) {
        this.repository = verifyTokenRepository;
    }

    public ServerResponseDto getVerifyToken(String email) {
        VerificationTokenEntity token = repository.getVerifyTokenByEmail(email);
        Calendar cal = Calendar.getInstance();
        if (token != null) {
            if (token.getTimeGetToken() != null && ((token.getTimeGetToken().getTime() - cal.getTime().getTime()) >= 0)) {
                return new ServerResponseDto(ResponseCase.ACTION_DONE_BEFORE);
            } else {
                token.setTimeGetToken(new Date());
                repository.save(token);
                return new ServerResponseDto(ResponseCase.OK, token.getToken());
            }


        } else {
            return new ServerResponseDto(ResponseCase.NOT_ACCESS);
        }
    }
}
