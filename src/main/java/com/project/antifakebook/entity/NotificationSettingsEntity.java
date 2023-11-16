package com.project.antifakebook.entity;

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
}
