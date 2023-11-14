package com.project.antifakebook.dto.request_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class    SetAcceptFriendRequestDto {
    private Long userId;
    private Integer isAccept;
}
