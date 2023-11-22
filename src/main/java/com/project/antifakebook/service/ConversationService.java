package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.conversation.*;
import com.project.antifakebook.entity.ConservationEntity;
import com.project.antifakebook.entity.MessageEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.ConservationRepository;
import com.project.antifakebook.repository.MessageRepository;
import com.project.antifakebook.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    private final ConservationRepository conservationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ConversationService(ConservationRepository conservationRepository,
                               MessageRepository messageRepository,
                               UserRepository userRepository) {
        this.conservationRepository = conservationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    public ServerResponseDto getListConversation(Long currentUserId,GetListConversationRequestDto requestDto) {
        GetListConversationResponseDto responseDto = new GetListConversationResponseDto();
        List<GetConversationResponseDto> list = new ArrayList<>();
        List<ConservationEntity> conservationEntities = conservationRepository
                .findByFromUserId(currentUserId, requestDto.getIndex(), requestDto.getCount());
        for(ConservationEntity entity : conservationEntities) {
            GetConversationResponseDto conversationResponseDto = new GetConversationResponseDto();
            conversationResponseDto.setId(entity.getId());
            UserEntity userEntity = userRepository.findById(entity.getToUserId()).orElse(null);
            assert userEntity != null;
            GetPartnerDto partnerDto = new GetPartnerDto(userEntity.getId(),userEntity.getName(), userEntity.getAvatarLink());
            conversationResponseDto.setPartner(partnerDto);
            MessageEntity lastMessage = messageRepository.findLastMessage(entity.getId());
            GetLastMessageDto lastMessageDto = null;
            if(lastMessage != null) {
                 lastMessageDto = new GetLastMessageDto(lastMessage.getContent(),lastMessage.getCreatedDate(),lastMessage.getIsRead());
            }
            conversationResponseDto.setLastMessage(lastMessageDto);
            list.add(conversationResponseDto);
        }
        responseDto.setConversations(list);
        responseDto.setNumNewMessage(messageRepository.getNewNumMessage(currentUserId));
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
}
