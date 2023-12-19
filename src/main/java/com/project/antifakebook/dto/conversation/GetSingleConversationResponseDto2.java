package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class GetSingleConversationResponseDto2 {
    private String message;
    private String messageId;
    private String unread;
    private String created;
    private GetSenderDto sender;
}
