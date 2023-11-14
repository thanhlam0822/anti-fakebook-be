package com.project.antifakebook.dto.friend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserFriendRequestDto {
    private Long userId;
    private Integer index;
    private Integer count;
}
