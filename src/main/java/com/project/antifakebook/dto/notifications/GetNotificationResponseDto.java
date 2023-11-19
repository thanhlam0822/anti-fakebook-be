package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


public interface GetNotificationResponseDto {
     String getType();
     Long getObjectId();
     String getTitle();
     Long getNotificationId();
     Date getCreated();
     String getAvatar();
     Integer getGroupType();
     Integer getIsRead();
}
