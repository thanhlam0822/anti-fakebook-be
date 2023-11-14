package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.request_friend.GetFriendRequestResponseDto;
import com.project.antifakebook.dto.request_friend.GetRequestedFriendsRequestDto;
import com.project.antifakebook.dto.request_friend.UserFriendResponseDto;
import com.project.antifakebook.entity.RequestFriendEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.FriendRepository;
import com.project.antifakebook.repository.RequestFriendRepository;
import com.project.antifakebook.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestFriendService {
    private final RequestFriendRepository requestFriendRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public RequestFriendService(RequestFriendRepository requestFriendRepository, UserRepository userRepository, FriendRepository friendRepository) {
        this.requestFriendRepository = requestFriendRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }
    public ServerResponseDto getRequestedFriends(Long userId,GetRequestedFriendsRequestDto requestDto) {
        GetFriendRequestResponseDto responseDto = new GetFriendRequestResponseDto();
        List<UserFriendResponseDto> userFriendResponseDtos = new ArrayList<>();
        List<RequestFriendEntity> requests = requestFriendRepository
                .getRequestFriendByUserId(userId, requestDto.getIndex(), requestDto.getCount());
        for(RequestFriendEntity requestFriend : requests) {
            UserFriendResponseDto userFriendResponseDto = new UserFriendResponseDto();
            userFriendResponseDto.setId(requestFriend.getId());
            UserEntity userEntity = userRepository.findById(requestFriend.getFriendId()).orElse(null);
            assert userEntity != null;
            userFriendResponseDto.setUsername(userEntity.getName());
            userFriendResponseDto.setAvatar(userEntity.getAvatarLink());
            userFriendResponseDto.setSameFriends(friendRepository.getSameFriendsAmount(userId, requestFriend.getFriendId()));
            userFriendResponseDto.setCreated(requestFriend.getCreatedDate());
            userFriendResponseDtos.add(userFriendResponseDto);
        }
        responseDto.setRequest(userFriendResponseDtos);
        responseDto.setTotal(requestFriendRepository.getTotalRequestOfUser(userId));
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }

}
