package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class GetSingleConversationResponseDto {
    private List<GetSingleConversationResponseDto2> conversation;
    private String isBlock;
}

