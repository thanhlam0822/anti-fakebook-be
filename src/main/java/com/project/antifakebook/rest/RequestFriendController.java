package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.request_friend.GetRequestedFriendsRequestDto;
import com.project.antifakebook.dto.request_friend.SetAcceptFriendRequestDto;
import com.project.antifakebook.service.RequestFriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request-friend")
public class RequestFriendController {
    private final RequestFriendService requestFriendService;

    public RequestFriendController(RequestFriendService requestFriendService) {
        this.requestFriendService = requestFriendService;
    }
    @PostMapping("/get-requested-friends")
    public ServerResponseDto getRequestedFriends(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                 @RequestBody GetRequestedFriendsRequestDto requestDto) {
        return requestFriendService.getRequestedFriends(currentUser.getUserId(),requestDto);
    }
    @PostMapping("/set-accept-friend")
    public ServerResponseDto setAcceptFriend(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @RequestBody SetAcceptFriendRequestDto requestDto) {
        return requestFriendService.checkIsAcceptFriend(currentUser.getUserId(),requestDto);
    }
    @PostMapping("/get-list-suggested-friends")
    public ServerResponseDto getListSuggestedFriends(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                     @RequestBody GetRequestedFriendsRequestDto requestDto) {
        return requestFriendService.getListSuggestedFriends(currentUser.getUserId(),requestDto);
    }
    @GetMapping("/set-request-friend")
    public ServerResponseDto setRequestFriend(@AuthenticationPrincipal CustomUserDetails currentUser,
                                              @RequestParam Long setFriendId) {
        return requestFriendService.setRequestFriend(currentUser.getUserId(),setFriendId);
    }
}
