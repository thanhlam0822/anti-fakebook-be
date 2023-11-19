package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.notification_settings.SetPushSettingRequestDto;
import com.project.antifakebook.service.NotificationSettingsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-settings")
public class NotificationSettingsController {
    private final NotificationSettingsService notificationSettingsService;

    public NotificationSettingsController(NotificationSettingsService notificationSettingsService) {
        this.notificationSettingsService = notificationSettingsService;
    }
    @GetMapping("/get-push-settings")
    public ServerResponseDto getPushSettings(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return notificationSettingsService.getPushSettings(currentUser.getUserId());
    }
    @PostMapping("/set-push-settings")
    public ServerResponseDto setPushSettings(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @RequestBody SetPushSettingRequestDto requestDto) {
        return notificationSettingsService.setPushSettings(currentUser.getUserId(),requestDto);
    }
}
