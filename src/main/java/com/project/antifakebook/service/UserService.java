package com.project.antifakebook.service;


import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.AccountLoginRequestDto;
import com.project.antifakebook.dto.account.AccountLoginResponseDto;
import com.project.antifakebook.dto.account.RegisterAccountRequestDto;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.entity.VerificationTokenEntity;
import com.project.antifakebook.events.OnRegistrationCompleteEvent;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.repository.VerifyTokenRepository;
import com.project.antifakebook.util.ValidateRegisterAccountRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private ApplicationEventPublisher eventPublisher;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private VerifyTokenRepository verifyTokenRepository;

    public UserService() {
    }

    @Autowired
    public UserService(ApplicationEventPublisher eventPublisher,
                       PasswordEncoder encoder, AuthenticationManager authenticationManage,
                       JwtService jwtService,VerifyTokenRepository verifyTokenRepository) {
        this.encoder = encoder;
        this.authenticationManager = authenticationManage;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
        this.verifyTokenRepository = verifyTokenRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = userRepository.findByEmail(email);
        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ServerResponseDto registerAccount(RegisterAccountRequestDto requestDto, HttpServletRequest servletRequest) {
        try {

            if (ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail())
                    && ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword(), requestDto.getEmail())) {
                UserEntity user = new UserEntity(requestDto.getEmail(), encoder.encode(requestDto.getPassword()));
                userRepository.save(user);
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user.getId(),
                        servletRequest.getLocale(), getAppUrl(servletRequest)));
                return new ServerResponseDto(ResponseCase.OK);
            } else if (!ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail()) &&
                    ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword(), requestDto.getEmail())) {
                return new ServerResponseDto(ResponseCase.INVALID_EMAIL);
            } else if (ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail()) &&
                    !ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword(), requestDto.getEmail())) {
                return new ServerResponseDto(ResponseCase.INVALID_PASSWORD);
            } else if (requestDto.getEmail().isEmpty() && requestDto.getPassword().isEmpty()) {
                return new ServerResponseDto(ResponseCase.INVALID_EMAIL);
            } else {
                return new ServerResponseDto(ResponseCase.INVALID_EMAIL_AND_PASSWORD);
            }
        } catch (DataIntegrityViolationException e) {
            return new ServerResponseDto(ResponseCase.USER_EXISTED);
        }
    }

    public ServerResponseDto login(AccountLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            String token = jwtService.generateToken(requestDto.getEmail());
            UserEntity userEntity = findByEmail(email);
            AccountLoginResponseDto responseDto = new AccountLoginResponseDto(
                    userEntity.getId(),
                    userEntity.getName(),
                    token,
                    userEntity.getAvatarLink(),
                    userEntity.getActiveStatus(),
                    userEntity.getCoins());
            return new ServerResponseDto(ResponseCase.OK, responseDto);

        } else {
            return new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
    }

    public String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    public UserEntity createVerificationTokenForUser(Long userId, String token) {
        VerificationTokenEntity myToken = new VerificationTokenEntity(token, userId);
        verifyTokenRepository.save(myToken);
        return userRepository.findById(userId).orElse(null);
    }
    public UserEntity updateVerificationTokenForUser(Long userId) {
        VerificationTokenEntity token = verifyTokenRepository.getVerifyTokenByUserId(userId);
        String newToken = UUID.randomUUID().toString();
        token.setGetToken(false);
        token.setToken(newToken);
        token.setExpiryDate(token.calculateExpiryDate());
        verifyTokenRepository.save(token);
        return userRepository.findById(userId).orElse(null);
    }
}

