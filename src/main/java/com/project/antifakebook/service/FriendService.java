package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.friend.GetUserFriendRequestDto;
import com.project.antifakebook.dto.friend.GetUserFriendResponseDto;

import com.project.antifakebook.dto.request_friend.UserFriendResponseDto;
import com.project.antifakebook.entity.FriendEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.FriendRepository;

import com.project.antifakebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository,UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }
    public ServerResponseDto getUserFriend(GetUserFriendRequestDto requestDto) {
        List<GetUserFriendResponseDto> responseDtos = new ArrayList<>();
        List<FriendEntity> friendEntities = friendRepository.getFriendEntitiesByUserId
                (requestDto.getUserId(), requestDto.getIndex(),requestDto.getCount());
        for(FriendEntity friendEntity : friendEntities) {
            GetUserFriendResponseDto responseDto = new GetUserFriendResponseDto();
            UserFriendResponseDto userFriendResponseDto = new UserFriendResponseDto();
            userFriendResponseDto.setId(friendEntity.getFriendId().toString());
            UserEntity userEntity = userRepository.findById(friendEntity.getFriendId()).orElse(null);
            assert userEntity != null;
            userFriendResponseDto.setUsername(userEntity.getName());
            userFriendResponseDto.setAvatar(userEntity.getAvatarLink());
            userFriendResponseDto.setSameFriends(friendRepository.
                        getSameFriendsAmount(requestDto.getUserId(),friendEntity.getFriendId()).toString());
            userFriendResponseDto.setCreated(friendEntity.getCreatedDate().toString());
            responseDto.setFriends(userFriendResponseDto);
            responseDto.setTotal(friendRepository.countFriendEntitiesByUserId(userEntity.getId()).toString());
            responseDtos.add(responseDto);
        }

        return new ServerResponseDto(ResponseCase.OK,responseDtos);
    }

}
