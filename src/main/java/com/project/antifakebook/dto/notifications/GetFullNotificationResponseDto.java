package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetFullNotificationResponseDto {
    private List<GetNotificationResponseDto> notifications;
    private Integer badge;
    private Date lastUpdate;
}
