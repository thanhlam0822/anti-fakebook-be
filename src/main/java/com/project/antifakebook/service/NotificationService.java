package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.notifications.*;
import com.project.antifakebook.entity.NotificationEntity;
import com.project.antifakebook.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public ServerResponseDto getNotification(Long userId,GetNotificationRequestDto requestDto) {
        List<GetNotificationResponseDto> notifications =
                notificationRepository.getNotifications(userId,requestDto.getIndex(), requestDto.getCount());
        List<GetNotificationResponseStringDto> responseDtos = new ArrayList<>();
        for(GetNotificationResponseDto dto : notifications) {
            GetNotificationResponseStringDto responseDto = new GetNotificationResponseStringDto(dto);
            responseDtos.add(responseDto);
        }
        GetFullNotificationResponseDto responseDto = new GetFullNotificationResponseDto();
        responseDto.setNotifications(responseDtos);
        responseDto.setBadge(notificationRepository.countBadge(userId).toString());
        responseDto.setLastUpdate(notificationRepository.lastUpdate(userId).toString());
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
    public ServerResponseDto setReadNotification(Long notificationId,Long userId) {
        SetReadNotificationResponseDto responseDto ;
        ServerResponseDto serverResponseDto = null;
        NotificationEntity notificationEntity = notificationRepository
                .findByIdAndUserIdAndIsRead(notificationId,userId,0);
        if(notificationEntity != null) {
            notificationEntity.setIsRead(1);
            notificationRepository.save(notificationEntity);
            Integer badge = notificationRepository.countBadge(userId);
            Date lastUpdate = notificationRepository.lastUpdate(userId);
            responseDto = new SetReadNotificationResponseDto(badge,lastUpdate);
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
        }
        return serverResponseDto;
    }
}
