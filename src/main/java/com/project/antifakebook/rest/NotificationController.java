package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.notifications.GetNotificationRequestDto;
import com.project.antifakebook.service.NotificationService;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/get-notification")
    public ServerResponseDto getNotification(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @RequestBody GetNotificationRequestDto requestDto) {
        return notificationService.getNotification(currentUser.getUserId(),requestDto);
    }
    @GetMapping("/set-read-notification")
    public ServerResponseDto setReadNotification(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                 @RequestParam Long notificationId) {
        return notificationService.setReadNotification(notificationId,currentUser.getUserId());
    }
}
