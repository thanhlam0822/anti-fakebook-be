package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.conversation.GetListConversationRequestDto;
import com.project.antifakebook.service.ConversationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
