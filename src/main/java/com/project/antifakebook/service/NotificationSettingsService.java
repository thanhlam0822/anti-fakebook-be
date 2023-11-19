package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.notification_settings.GetPushSettingsResponseDto;
import com.project.antifakebook.dto.notification_settings.SetPushSettingRequestDto;
import com.project.antifakebook.entity.NotificationSettingsEntity;
import com.project.antifakebook.repository.NotificationSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        ServerResponseDto serverResponseDto ;
        NotificationSettingsEntity notificationSettingsEntity = notificationSettingsRepository.findByUserId(userId);
        if(notificationSettingsEntity != null) {
            notificationSettingsEntity.setLikeComment(requestDto.getLikeComment());
            notificationSettingsEntity.setFromFriends(requestDto.getFromFriends());
            notificationSettingsEntity.setRequestedFriend(requestDto.getRequestedFriend());
            notificationSettingsEntity.setSuggestedFriend(requestDto.getSuggestedFriend());
            notificationSettingsEntity.setBirthday(requestDto.getBirthday());
            notificationSettingsEntity.setVideo(requestDto.getVideo());
            notificationSettingsEntity.setReport(requestDto.getReport());
            notificationSettingsEntity.setSoundOn(requestDto.getSoundOn());
            notificationSettingsEntity.setNotificationOn(requestDto.getNotificationOn());
            notificationSettingsEntity.setVibrantOn(requestDto.getVibrantOn());
            notificationSettingsEntity.setLedOn(requestDto.getLedOn());
            notificationSettingsEntity.setEditDate(new Date());
            notificationSettingsRepository.save(notificationSettingsEntity);
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,notificationSettingsEntity);
        } else {
            NotificationSettingsEntity settingsEntity = new NotificationSettingsEntity(requestDto);
            settingsEntity.setUserId(userId);
            notificationSettingsRepository.save(settingsEntity);
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,settingsEntity);
        }
        return serverResponseDto;

    }

}
