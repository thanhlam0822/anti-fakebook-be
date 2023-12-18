package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class GetNotificationResponseStringDto {
    private String type;
    private String objectId;
    private String title;
    private String notificationId;
    private String created;
    private String avatar;
    private String groupType;
    private String isRead;

    public GetNotificationResponseStringDto(GetNotificationResponseDto responseDto) {
        this.type = responseDto.getType();
        this.objectId = responseDto.getObjectId().toString();
        this.title = responseDto.getTitle();
        this.notificationId = responseDto.getNotificationId().toString();
        this.created = responseDto.getCreated().toString();
        this.avatar = responseDto.getAvatar();
        this.groupType = responseDto.getType();
        this.isRead = responseDto.getIsRead().toString();
    }
}
