package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.conversation.*;
import com.project.antifakebook.service.ConversationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping("/get-list-conversation")
    public ServerResponseDto getListConversation(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                 @RequestBody GetListConversationRequestDto requestDto) {
        return conversationService.getListConversation(currentUser.getUserId(),requestDto);
    }
    @PostMapping("/get-conversation")
    public ServerResponseDto getConversation(@AuthenticationPrincipal CustomUserDetails currentUser,
                                             @RequestBody GetSingleConversationRequestDto requestDto) {
        return conversationService.getConversation(currentUser.getUserId(),requestDto);
    }
    @PostMapping("/set-read-message")
    public ServerResponseDto setReadMessage(@RequestBody SetReadMessageRequestDto requestDto) {
        return conversationService.setReadMessage(requestDto.getPartnerId(), requestDto.getConversationId());
    }
    @PostMapping("/delete-message")
    public ServerResponseDto deleteMessage(@RequestBody DeleteMessageRequestDto requestDto) {
        return conversationService.deleteMessage(requestDto);
    }
    @PostMapping("/delete-conversation")
    public ServerResponseDto deleteConversation(@RequestBody DeleteConversationRequestDto requestDto) {
        return conversationService.deleteConversation(requestDto);
    }
}
