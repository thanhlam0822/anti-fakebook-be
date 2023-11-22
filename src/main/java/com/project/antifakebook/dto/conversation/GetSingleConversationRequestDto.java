package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GetSingleConversationRequestDto {
    private Long partnerId;
    private Long conversationId;
    private Integer index;
    private Integer count;
}
