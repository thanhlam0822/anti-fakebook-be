package com.project.antifakebook.dto.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetFullNotificationResponseDto {
    private List<GetNotificationResponseStringDto> notifications;
    private String badge;
    private String lastUpdate;
}
