package com.project.antifakebook.dto.request_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;

@NoArgsConstructor
@Getter
@Setter
public class ListFriendSuggestedResponseDto {
    private String userId;
    private String username;
    private String avatar;
    private String sameFriends;
}
