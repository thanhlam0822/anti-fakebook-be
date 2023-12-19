package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.conversation.*;
import com.project.antifakebook.entity.ConversationEntity;
import com.project.antifakebook.entity.MessageEntity;
import com.project.antifakebook.entity.UserEntity;
import com.project.antifakebook.repository.ConversationRepository;
import com.project.antifakebook.repository.MessageRepository;
import com.project.antifakebook.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public ConversationService(ConversationRepository conversationRepository,
                               MessageRepository messageRepository,
                               UserRepository userRepository,
                               PostService postService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.postService = postService;
    }
    public ServerResponseDto getListConversation(Long currentUserId,GetListConversationRequestDto requestDto) {
        GetListConversationResponseDto responseDto = new GetListConversationResponseDto();
        List<GetConversationResponseDto> list = new ArrayList<>();
        List<ConversationEntity> conservationEntities = conversationRepository
                .findByFromUserId(currentUserId, requestDto.getIndex(), requestDto.getCount());
        for(ConversationEntity entity : conservationEntities) {
            GetConversationResponseDto conversationResponseDto = new GetConversationResponseDto();
            conversationResponseDto.setId(entity.getId().toString());
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
    public ServerResponseDto getConversation(Long currentUserId,GetSingleConversationRequestDto requestDto) {
        GetSingleConversationResponseDto responseDto = new GetSingleConversationResponseDto();
        List<GetSingleConversationResponseDto2> conversation = new ArrayList<>();
        List<MessageEntity> messageEntities = messageRepository
                .findByToUserIdAndConversationId(requestDto.getPartnerId(), requestDto.getConversationId(), requestDto.getIndex(), requestDto.getCount());
        if(!messageEntities.isEmpty()) {
            for(MessageEntity messageEntity :  messageEntities) {
                GetSingleConversationResponseDto2 singleConversation = new GetSingleConversationResponseDto2();
                singleConversation.setMessage(messageEntity.getContent());
                singleConversation.setMessageId(messageEntity.getId().toString());
                singleConversation.setUnread(messageEntity.getIsRead().toString());
                singleConversation.setCreated(messageEntity.getCreatedDate().toString());
                userRepository.findById(messageEntity.getFromUserId())
                        .ifPresent(userEntity -> singleConversation.setSender(new GetSenderDto(userEntity.getId(), userEntity.getName(), userEntity.getAvatarLink())));
                conversation.add(singleConversation);

            }
            responseDto.setConversation(conversation);
            int isBlockStatus = 0;
            Boolean isBlock = postService.isAuthorBlockCurrentUser(currentUserId,requestDto.getPartnerId());
            if(Boolean.TRUE.equals(isBlock)) {
                isBlockStatus = 1;
            }
            responseDto.setIsBlock(String.valueOf(isBlockStatus));

        }
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
    public ServerResponseDto setReadMessage(Long partnerId,Long conversationId) {
        messageRepository.setReadMessage(partnerId,conversationId);
        return new ServerResponseDto(ResponseCase.OK);
    }
    public ServerResponseDto deleteMessage(@RequestBody DeleteMessageRequestDto requestDto) {
        Optional<MessageEntity> messageEntity = messageRepository.findById(requestDto.getMessageId());
        if(messageEntity.isPresent()) {
            messageRepository.deleteById(requestDto.getMessageId());
        } else {
            return new ServerResponseDto(ResponseCase.INVALID_PARAMETER);
        }
        return new ServerResponseDto(ResponseCase.OK);
    }
    public ServerResponseDto deleteConversation(DeleteConversationRequestDto requestDto) {
        Optional<ConversationEntity> conversationEntity = conversationRepository.findById(requestDto.getConversationId());
        List<MessageEntity> messageEntities = messageRepository.findByConversationId(requestDto.getConversationId());
        if(conversationEntity.isPresent()) {
            conversationRepository.deleteById(requestDto.getConversationId());
            if(!messageEntities.isEmpty()) {
                conversationRepository.deleteAllByConversationId(requestDto.getConversationId());
            }
        } else {
            return new ServerResponseDto(ResponseCase.INVALID_PARAMETER);
        }
        return new ServerResponseDto(ResponseCase.OK);
    }

}
