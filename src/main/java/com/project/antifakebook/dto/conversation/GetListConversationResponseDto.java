package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class GetListConversationResponseDto {
    private List<GetConversationResponseDto> conversations;
    private Integer numNewMessage;
}
