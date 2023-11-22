package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetConversationResponseDto {
    private Long id;
    private GetPartnerDto partner;
    private GetLastMessageDto lastMessage;
}
