package com.project.antifakebook.dto.request_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserFriendResponseDto {
    private String id;
    private String username;
    private String avatar;
    private String sameFriends;
    private String created;
}
