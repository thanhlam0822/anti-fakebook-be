package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SetReadNotificationResponseDto {
    private Integer badge;
    private Date lastUpdate;

    public SetReadNotificationResponseDto(Integer badge, Date lastUpdate) {
        this.badge = badge;
        this.lastUpdate = lastUpdate;
    }
}
