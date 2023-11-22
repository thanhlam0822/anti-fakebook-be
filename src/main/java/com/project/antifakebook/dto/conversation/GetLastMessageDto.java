package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GetLastMessageDto {
    private String message;
    private Date created;
    private Integer unread;

    public GetLastMessageDto(String message, Date created, Integer unread) {
        this.message = message;
        this.created = created;
        this.unread = unread;
    }
}
