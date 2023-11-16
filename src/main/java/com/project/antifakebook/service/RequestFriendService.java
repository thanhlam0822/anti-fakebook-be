package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.request_friend.*;
import com.project.antifakebook.entity.FriendEntity;
import com.project.antifakebook.entity.RequestFriendEntity;
import com.project.antifakebook.entity.SuggestedFriendEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.FriendRepository;
import com.project.antifakebook.repository.RequestFriendRepository;
import com.project.antifakebook.repository.SuggestedFriendRepository;
import com.project.antifakebook.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class RequestFriendService {
    private final RequestFriendRepository requestFriendRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final SuggestedFriendRepository suggestedFriendRepository;

    public RequestFriendService(RequestFriendRepository requestFriendRepository,
                                UserRepository userRepository,
                                FriendRepository friendRepository,
                                SuggestedFriendRepository suggestedFriendRepository) {
        this.requestFriendRepository = requestFriendRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.suggestedFriendRepository = suggestedFriendRepository;
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
    public ServerResponseDto getListSuggestedFriends(Long currentUserId ,GetRequestedFriendsRequestDto requestDto) {
        GetListSuggestedFriendResponseDto responseDto = new GetListSuggestedFriendResponseDto();
        List<SuggestedFriendEntity> suggestedFriendEntities =
                suggestedFriendRepository.getListSuggestedFriends(currentUserId,requestDto.getIndex(), requestDto.getCount());
        List<ListFriendSuggestedResponseDto> list = new ArrayList<>();
        for(SuggestedFriendEntity suggestedFriend : suggestedFriendEntities) {
            UserEntity userEntity = userRepository.findById(suggestedFriend.getFriendId()).orElse(null);
            assert userEntity != null;
            ListFriendSuggestedResponseDto dto = new ListFriendSuggestedResponseDto();
            dto.setUserId(userEntity.getId());
            dto.setUsername(userEntity.getName());
            dto.setAvatar(userEntity.getAvatarLink());
            dto.setSameFriends(friendRepository.getSameFriendsAmount(currentUserId,suggestedFriend.getFriendId()));
            list.add(dto);
        }
        responseDto.setListUsers(list);
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
    public ServerResponseDto setRequestFriend(Long currentUserId,Long userId) {

        ServerResponseDto serverResponseDto;
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isPresent()) {
            RequestFriendEntity oldRequest = requestFriendRepository
                    .findRequestFriendEntitiesByUserIdAndFriendIdAndIsAcceptFalse(currentUserId, userId);
            if(oldRequest != null) {
                serverResponseDto = new ServerResponseDto(ResponseCase.ACTION_DONE_BEFORE);
            } else if (currentUserId == userId) {
                serverResponseDto = new ServerResponseDto(ResponseCase.INVALID_PARAMETER);
            }
            else {
                RequestFriendEntity requestFriendEntity = new RequestFriendEntity(currentUserId,userId);
                requestFriendRepository.save(requestFriendEntity);
                SetRequestFriendResponseDto responseDto = new SetRequestFriendResponseDto();
                responseDto.setRequestedFriends(requestFriendRepository.countRequestFriendNumber(currentUserId));
                serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
            }
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.USER_IS_NOT_VALIDATED);
        }
        return serverResponseDto;
    }

}
