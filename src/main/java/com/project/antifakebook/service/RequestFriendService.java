package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.request_friend.GetFriendRequestResponseDto;
import com.project.antifakebook.dto.request_friend.GetRequestedFriendsRequestDto;
import com.project.antifakebook.dto.request_friend.SetAcceptFriendRequestDto;
import com.project.antifakebook.dto.request_friend.UserFriendResponseDto;
import com.project.antifakebook.entity.FriendEntity;
import com.project.antifakebook.entity.RequestFriendEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.FriendRepository;
import com.project.antifakebook.repository.RequestFriendRepository;
import com.project.antifakebook.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public ServerResponseDto setAcceptFriend(Long currentUserId, SetAcceptFriendRequestDto requestDto) {
        ServerResponseDto serverResponseDto;
        RequestFriendEntity requestFriend = requestFriendRepository.findRequestFriendEntitiesByUserIdAndFriendIdAndIsAcceptFalse(currentUserId,requestDto.getUserId());
        if(requestFriend != null) {
            if(requestDto.getIsAccept() == 1) {
                requestFriend.setIsAccept(true);
                requestFriend.setEditDate(new Date());
                FriendEntity friend1 = new FriendEntity(currentUserId,requestDto.getUserId());
                FriendEntity friend2 = new FriendEntity(requestDto.getUserId(),currentUserId);
                requestFriendRepository.save(requestFriend);
                friendRepository.save(friend1);
                friendRepository.save(friend2);
            } else {
                requestFriendRepository.deleteById(requestFriend.getId());
            }
            serverResponseDto = new ServerResponseDto(ResponseCase.OK);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.INVALID_PARAMETER);
        }
        return serverResponseDto;
    }
    public ServerResponseDto checkIsAcceptFriend(Long currentUserId, SetAcceptFriendRequestDto requestDto) {
        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendId(currentUserId,requestDto.getUserId());
        ServerResponseDto serverResponseDto;
        if(friendEntity == null) {
           serverResponseDto = setAcceptFriend(currentUserId,requestDto);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.ACTION_DONE_BEFORE);
        }
        return serverResponseDto;
    }

}
