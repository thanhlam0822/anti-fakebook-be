package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GetSingleConversationResponseDto2 {
    private String message;
    private Long messageId;
    private Integer unread;
    private Date createdDate;
    private GetSenderDto sender;
}
