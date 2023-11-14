package com.project.antifakebook.rest;


import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.friend.GetUserFriendRequestDto;
import com.project.antifakebook.service.FriendService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }
    @PostMapping("/get-user-friends")
    public ServerResponseDto getUserFriend(@RequestBody GetUserFriendRequestDto requestDto) {
        return friendService.getUserFriend(requestDto);
    }
}
