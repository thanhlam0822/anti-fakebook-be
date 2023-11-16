package com.project.antifakebook.dto.notification_settings;

import com.project.antifakebook.entity.NotificationSettingsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetPushSettingsResponseDto {
    private Integer likeComment;
    private Integer fromFriends;
    private Integer requestedFriend;
    private Integer suggestedFriend;
    private Integer birthday;
    private Integer video;
    private Integer report;
    private Integer soundOn;
    private Integer notificationOn;
    private Integer vibrantOn;
    private Integer ledOn;

    public GetPushSettingsResponseDto(NotificationSettingsEntity entity) {
        this.likeComment = entity.getLikeComment();
        this.fromFriends = entity.getFromFriends();
        this.requestedFriend = entity.getRequestedFriend();
        this.suggestedFriend = entity.getSuggestedFriend();
        this.birthday = entity.getBirthday();
        this.video = entity.getVideo();
        this.report = entity.getReport();
        this.soundOn = entity.getSoundOn();
        this.notificationOn = entity.getNotificationOn();
        this.vibrantOn = entity.getVibrantOn();
        this.ledOn = entity.getLedOn();
    }
}
