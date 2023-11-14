package com.project.antifakebook.dto.friend;

import com.project.antifakebook.dto.request_friend.UserFriendResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetUserFriendResponseDto {
    private UserFriendResponseDto friends;
    private Integer total;
}
