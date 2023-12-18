package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SetReadNotificationResponseDto {
    private String badge;
    private String lastUpdate;

    public SetReadNotificationResponseDto(Integer badge, Date lastUpdate) {
        this.badge = badge.toString();
        this.lastUpdate = lastUpdate.toString();
    }
}
