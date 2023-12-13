package com.project.antifakebook.service;


import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.account.*;

import com.project.antifakebook.entity.FriendEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.entity.VerificationTokenEntity;

import com.project.antifakebook.enums.ActiveStatusCode;

import com.project.antifakebook.repository.FriendRepository;
import com.project.antifakebook.repository.UserRepository;
import com.project.antifakebook.repository.VerifyTokenRepository;
import com.project.antifakebook.util.FileUploadUtil;
import com.project.antifakebook.util.OTPUtils;
import com.project.antifakebook.util.ValidateRegisterAccountRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


import java.util.Objects;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private VerifyTokenRepository verifyTokenRepository;
    private FriendRepository friendRepository;

    public UserService() {
    }

    @Autowired
    public UserService(PasswordEncoder encoder, AuthenticationManager authenticationManage,
                       JwtService jwtService,VerifyTokenRepository verifyTokenRepository,
                       FriendRepository friendRepository
                       ) {
        this.encoder = encoder;
        this.authenticationManager = authenticationManage;
        this.jwtService = jwtService;
        this.verifyTokenRepository = verifyTokenRepository;
        this.friendRepository = friendRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userDetail = userRepository.findUserByEmailCustom(email);
        if (userDetail == null) {
            throw new UsernameNotFoundException("User not found " + email);
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(userDetail);
        customUserDetails.setUserId(userDetail.getId());
        customUserDetails.setCoins(userDetail.getCoins());
        return customUserDetails;

    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ServerResponseDto registerAccount(RegisterAccountRequestDto requestDto) {
        try {

            if (ValidateRegisterAccountRequestUtils.validateEmail(requestDto.getEmail())
                    && ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getPassword(), requestDto.getEmail())) {
                UserEntity user = new UserEntity(requestDto.getEmail(), encoder.encode(requestDto.getPassword()));
                userRepository.save(user);
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
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken) && isValidateUser(authentication.getName())) {
            String email = authentication.getName();
            String token = jwtService.generateToken(requestDto.getEmail());
            UserEntity userEntity = findByEmail(email);
            userEntity.setOnline(true);
            userRepository.save(userEntity);
            AccountLoginResponseDto responseDto = new AccountLoginResponseDto(
                    userEntity.getId().toString(),
                    userEntity.getName(),
                    token,
                    userEntity.getAvatarLink(),
                    userEntity.getActiveStatus().toString(),
                    userEntity.getCoins().toString());
            return new ServerResponseDto(ResponseCase.OK, responseDto);

        } else {
            return new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
    }
    public ServerResponseDto changeInfoAfterSignUp
            (Authentication authentication,String username,MultipartFile file) throws IOException {
        ServerResponseDto serverResponseDto;
        String userEmail = authentication.getName();
        ChangeInfoAfterSignUpResponseDto responseDto ;
            UserEntity userEntity = userRepository.findByEmail(userEmail).orElse(null);
            boolean isValidUsername = ValidateRegisterAccountRequestUtils.isValidUsername(username);
            if(isValidUsername) {
                Objects.requireNonNull(userEntity).setName(username);
                userEntity.setAvatarLink(uploadAvatarImageFile(userEntity.getId(), file));
                userRepository.save(userEntity);
                responseDto = new ChangeInfoAfterSignUpResponseDto(userEntity);
                serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
            }
            else {
                serverResponseDto = new ServerResponseDto(ResponseCase.INVALID_USER_NAME);
            }
            return serverResponseDto;
    }
    public String uploadAvatarImageFile(Long userId,MultipartFile multipartFile) throws IOException {
        String fileName = "avatar_"+userId+".jpg";
        String uploadDir = "src/main/resources/avatar/";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return uploadDir + fileName;
    }
    public String uploadCoverImage(Long userId,MultipartFile multipartFile) throws IOException {
        String fileName = "cover_image_"+userId+".jpg";
        String uploadDir = "src/main/resources/cover_image/";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return uploadDir + fileName;
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
        String newToken = OTPUtils.generateOTP();
        token.setGetToken(false);
        token.setToken(newToken);
        token.setExpiryDate(token.calculateExpiryDate());
        verifyTokenRepository.save(token);
        return userRepository.findById(userId).orElse(null);
    }
    public boolean isValidateUser(String email) {
        UserEntity user = findByEmail(email);
        return user.getActiveStatus().equals(ActiveStatusCode.ACTIVE.getCode());

    }
    public ServerResponseDto changePassword(Long currentUserId,String currentUserEmail,
                                            ChangePasswordRequestDto requestDto) {
        ServerResponseDto serverResponseDto;
        UserEntity userEntity = userRepository.findById(currentUserId).orElse(null);
        if(userEntity != null) {
            if(ValidateRegisterAccountRequestUtils.validatePassword(requestDto.getNewPassword(),currentUserEmail)
            && encoder.matches(requestDto.getPassword(),userEntity.getPassword())) {
                userEntity.setPassword(encoder.encode(requestDto.getNewPassword()));
                userRepository.save(userEntity);
                serverResponseDto = new ServerResponseDto(ResponseCase.OK);
            } else {
                serverResponseDto = new ServerResponseDto(ResponseCase.INVALID_PASSWORD);
            }
        } else {
                serverResponseDto = new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
        return serverResponseDto;
    }
    public ServerResponseDto getUserInfo(Long userId,Long currentUserId) {
        ServerResponseDto serverResponseDto;
        Integer isFriend = null;
        int online = 0;
        Integer coins = null;
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isPresent()) {
            Integer listing = friendRepository.countFriendEntitiesByUserId(userId);
            if(!userId.equals(currentUserId)) {
                FriendEntity friendEntity = friendRepository.findByUserIdAndFriendId(currentUserId,userId);
                if(friendEntity != null) {
                    isFriend = 1;
                }
            } else {
                coins = userRepository.getCoinsOfUser(currentUserId);
            }
            if(Boolean.TRUE.equals(userEntity.get().getOnline())) {
                online = 1;
            }
            GetUserInfoResponseDto responseDto = new
                    GetUserInfoResponseDto(userEntity.get(),listing,isFriend,online,coins);
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
        return serverResponseDto;
    }
    public ServerResponseDto setUserInfo
            (Long userId,SetUserInfoRequestDto requestDto,MultipartFile avatar,MultipartFile coverImage) throws IOException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        ServerResponseDto serverResponseDto;
        if(userEntity.isPresent()) {
            UserEntity currentUser = userEntity.get();
            currentUser.setName(requestDto.getUsername());
            currentUser.setDescription(requestDto.getDescription());
            currentUser.setAvatarLink(uploadAvatarImageFile(userId,avatar));
            currentUser.setAddress(requestDto.getAddress());
            currentUser.setCity(requestDto.getCity());
            currentUser.setCountry(requestDto.getCountry());
            currentUser.setCoverImage(uploadCoverImage(userId,coverImage));
            currentUser.setLink(requestDto.getLink());
            userRepository.save(currentUser);
            SetUserInfoResponseDto responseDto =
                    new SetUserInfoResponseDto(currentUser.getAvatarLink(),
                            currentUser.getCoverImage(),
                            currentUser.getLink(),
                            currentUser.getCity(),
                            currentUser.getCountry());
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
        return serverResponseDto;
    }
}

