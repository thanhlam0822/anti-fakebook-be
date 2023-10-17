package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.entity.VerificationTokenEntity;

import com.project.antifakebook.enums.ActiveStatusCode;
import com.project.antifakebook.events.ResendTokenEvent;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.repository.VerifyTokenRepository;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


@Service
public class VerifyTokenService {
    private final VerifyTokenRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public VerifyTokenService(VerifyTokenRepository verifyTokenRepository, UserService userService,
                              UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.repository = verifyTokenRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public ServerResponseDto getVerifyToken(String email, HttpServletRequest servletRequest) throws ParseException {
        UserEntity userEntity = userService.findByEmail(email);
        if (userEntity != null && userEntity.getActiveStatus().equals(ActiveStatusCode.ACTIVE.getCode())) {
            return new ServerResponseDto(ResponseCase.NOT_ACCESS);
        } else if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        } else {
            VerificationTokenEntity token = repository.getVerifyTokenByEmail(email);
            if (token != null) {
                if (isExpiredToken(token.getExpiryDate().toString())) {
                    if (token.isGetToken()) {
                        return new ServerResponseDto(ResponseCase.ACTION_DONE_BEFORE);
                    } else {
                        token.setGetToken(true);
                        repository.save(token);
                        return new ServerResponseDto(ResponseCase.OK, token.getToken());
                    }
                } else {
                    eventPublisher.publishEvent(new ResendTokenEvent(userEntity.getId(),
                            servletRequest.getLocale(), userService.getAppUrl(servletRequest)));
                    VerificationTokenEntity newToken = repository.getVerifyTokenByUserId(userEntity.getId());
                    return new ServerResponseDto(ResponseCase.OK, newToken.getToken());
                }
            } else {
                return new ServerResponseDto(ResponseCase.TOKEN_NULL);
            }
        }

    }

    public boolean isExpiredToken(String dateExpiredString) throws ParseException {
        String pattern = "yyyy-mm-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String currentDateString = simpleDateFormat.format(new Date());
        Date currentDate = simpleDateFormat.parse(currentDateString);
        Date dateExpired = simpleDateFormat.parse(dateExpiredString);
        return currentDate.before(dateExpired);
    }

    public ServerResponseDto confirmRegistration(String token) {
        UserEntity userEntity = userRepository.findUserByToken(token);
        if (userEntity != null) {
            userEntity.setActiveStatus(ActiveStatusCode.ACTIVE.getCode());
            userRepository.save(userEntity);
            return new ServerResponseDto(ResponseCase.OK);
        } else {
            return new ServerResponseDto(ResponseCase.INVALID_TOKEN);
        }
    }

}
