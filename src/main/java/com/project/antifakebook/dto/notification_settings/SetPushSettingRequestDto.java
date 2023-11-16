package com.project.antifakebook.dto.notification_settings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetPushSettingRequestDto {
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
}
