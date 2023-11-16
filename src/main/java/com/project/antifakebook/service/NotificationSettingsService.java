package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.notification_settings.GetPushSettingsResponseDto;
import com.project.antifakebook.dto.notification_settings.SetPushSettingRequestDto;
import com.project.antifakebook.entity.NotificationSettingsEntity;
import com.project.antifakebook.repository.NotificationSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationSettingsService {
    private final NotificationSettingsRepository notificationSettingsRepository;

    public NotificationSettingsService(NotificationSettingsRepository notificationSettingsRepository) {
        this.notificationSettingsRepository = notificationSettingsRepository;
    }
    public ServerResponseDto getPushSettings(Long userId) {
        ServerResponseDto serverResponseDto ;
        NotificationSettingsEntity notificationSettingsEntity = notificationSettingsRepository.findByUserId(userId);
        if(notificationSettingsEntity != null) {
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,new GetPushSettingsResponseDto(notificationSettingsEntity));
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.OK);
        }
        return serverResponseDto;
    }
    public ServerResponseDto setPushSettings(Long userId, SetPushSettingRequestDto requestDto) {
//        ServerResponseDto serverResponseDto ;
//        NotificationSettingsEntity notificationSettingsEntity = notificationSettingsRepository.findByUserId(userId);
//        if(notificationSettingsEntity != null) {
//            notificationSettingsEntity.setLikeComment(requestDto.getLikeComment());
//            notificationSettingsEntity.setFromFriends();
//        } else {
//            serverResponseDto = new ServerResponseDto(ResponseCase.OK);
//        }
//        return serverResponseDto;
        return null;
    }

}
