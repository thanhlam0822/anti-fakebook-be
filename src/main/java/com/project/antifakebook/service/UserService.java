package com.project.antifakebook.service;


import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.AccountLoginRequestDto;
import com.project.antifakebook.dto.account.RegisterAccountRequestDto;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.util.ValidateRegisterAccountRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManage, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManage;
        this.jwtService = jwtService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = userRepository.findByEmail(email);
        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public ServerResponseDto registerAccount(RegisterAccountRequestDto requestDto) {
        try {
            if (ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail())
                    && ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword())) {
                UserEntity user = new UserEntity(requestDto.getEmail(), encoder.encode(requestDto.getPassword()));
                userRepository.save(user);
                return new ServerResponseDto(ResponseCase.OK);
            } else if (!ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail())) {
                return new ServerResponseDto(ResponseCase.INVALID_EMAIL);
            } else if (!ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword())) {
                return new ServerResponseDto(ResponseCase.INVALID_PASSWORD);
            } else if (requestDto.getEmail().isEmpty() && requestDto.getPassword().isEmpty()) {
                return new ServerResponseDto(ResponseCase.INVALID_EMAIL);
            } else {
                return null;
            }
        } catch (DataIntegrityViolationException e) {
            return new ServerResponseDto(ResponseCase.USER_EXISTED);
        }
    }

    public String login(AccountLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(requestDto.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}

