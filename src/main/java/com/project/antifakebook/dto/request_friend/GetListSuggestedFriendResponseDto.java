package com.project.antifakebook.dto.request_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetListSuggestedFriendResponseDto {
        private List<ListFriendSuggestedResponseDto> listUsers;
}
