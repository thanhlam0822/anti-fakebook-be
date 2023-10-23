package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.entity.VerificationTokenEntity;

import com.project.antifakebook.enums.ActiveStatusCode;
import com.project.antifakebook.events.OnRegistrationCompleteEvent;
import com.project.antifakebook.events.ResendTokenEvent;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.repository.VerifyTokenRepository;


import com.project.antifakebook.util.ValidateRegisterAccountRequestUtils;
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

    public ServerResponseDto getVerifyTokenService(String email, HttpServletRequest servletRequest) throws ParseException {
        UserEntity userEntity = userService.findByEmail(email);
        if (userEntity != null && userEntity.getActiveStatus().equals(ActiveStatusCode.ACTIVE.getCode())) {
            return new ServerResponseDto(ResponseCase.NOT_ACCESS);
        } else if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        } else {
            VerificationTokenEntity token = repository.getVerifyTokenByEmail(email);
            return getVerifyToken(token, userEntity.getId(), servletRequest);
        }

    }

    public ServerResponseDto getVerifyToken(VerificationTokenEntity token, Long userId, HttpServletRequest servletRequest) throws ParseException {
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
                eventPublisher.publishEvent(new ResendTokenEvent(userId,
                        servletRequest.getLocale(), userService.getAppUrl(servletRequest)));
                VerificationTokenEntity newToken = repository.getVerifyTokenByUserId(userId);
                return new ServerResponseDto(ResponseCase.OK, newToken.getToken());
            }
        } else {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userId,
                    servletRequest.getLocale(), userService.getAppUrl(servletRequest)));
            VerificationTokenEntity tokenEntity = repository.getVerifyTokenByUserId(userId);
            tokenEntity.setGetToken(true);
            repository.save(tokenEntity);
            return new ServerResponseDto(ResponseCase.OK, tokenEntity.getToken());
        }
    }

    public ServerResponseDto checkVerifyCode(String token, String email) throws ParseException {
        VerificationTokenEntity tokenEntity = repository.getVerifyTokenByEmail(email);
        UserEntity userEntity = userRepository.findUserByTokenAndEmail(token, email);
        if (userEntity != null) {
            return activeUser(tokenEntity, userEntity);
        } else {
            return new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
    }

    public ServerResponseDto activeUser(VerificationTokenEntity tokenEntity,UserEntity userEntity) throws ParseException {
        if (tokenEntity != null && isExpiredToken(tokenEntity.getExpiryDate().toString())) {
            userEntity.setActiveStatus(ActiveStatusCode.ACTIVE.getCode());
            userRepository.save(userEntity);
            repository.deleteById(tokenEntity.getId());
            return new ServerResponseDto(ResponseCase.OK);
        } else {
            return new ServerResponseDto(ResponseCase.INVALID_TOKEN);
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
}
