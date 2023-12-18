package com.project.antifakebook.dto.request_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetFriendRequestResponseDto {
    private List<UserFriendResponseDto> request;
    private String total;
}
