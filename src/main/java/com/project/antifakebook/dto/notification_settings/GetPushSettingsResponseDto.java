package com.project.antifakebook.dto.notification_settings;

import com.project.antifakebook.entity.NotificationSettingsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetPushSettingsResponseDto {
    private String likeComment;
    private String fromFriends;
    private String requestedFriend;
    private String suggestedFriend;
    private String birthday;
    private String video;
    private String report;
    private String soundOn;
    private String notificationOn;
    private String vibrantOn;
    private String ledOn;

    public GetPushSettingsResponseDto(NotificationSettingsEntity entity) {
        this.likeComment = entity.getLikeComment().toString();
        this.fromFriends = entity.getFromFriends().toString();
        this.requestedFriend = entity.getRequestedFriend().toString();
        this.suggestedFriend = entity.getSuggestedFriend().toString();
        this.birthday = entity.getBirthday().toString();
        this.video = entity.getVideo().toString();
        this.report = entity.getReport().toString();
        this.soundOn = entity.getSoundOn().toString();
        this.notificationOn = entity.getNotificationOn().toString();
        this.vibrantOn = entity.getVibrantOn().toString();
        this.ledOn = entity.getLedOn().toString();
    }
}
