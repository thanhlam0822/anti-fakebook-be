package com.project.antifakebook.entity;

import com.project.antifakebook.dto.notification_settings.SetPushSettingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notification_settings")
public class NotificationSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Long userId;
    private Date editDate;
    public NotificationSettingsEntity(SetPushSettingRequestDto requestDto) {
        this.likeComment = requestDto.getLikeComment();
        this.fromFriends = requestDto.getFromFriends();
        this.requestedFriend = requestDto.getRequestedFriend();
        this.suggestedFriend = requestDto.getSuggestedFriend();
        this.birthday = requestDto.getBirthday();
        this.video = requestDto.getVideo();
        this.report = requestDto.getReport();
        this.soundOn = requestDto.getSoundOn();
        this.notificationOn = requestDto.getNotificationOn();
        this.vibrantOn = requestDto.getVibrantOn();
        this.ledOn = requestDto.getLedOn();
        this.editDate = new Date();
    }
}
