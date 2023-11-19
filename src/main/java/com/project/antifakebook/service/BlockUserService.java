package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.block_user.GetListBlockRequestDto;
import com.project.antifakebook.dto.block_user.GetListBlocksResponseDto;
import com.project.antifakebook.entity.BlockUserEntity;
import com.project.antifakebook.repository.BlockUserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlockUserService {
    private final BlockUserRepository blockUserRepository;

    public BlockUserService(BlockUserRepository blockUserRepository) {
        this.blockUserRepository = blockUserRepository;
    }
    public ServerResponseDto getListBlocks(Long currentUserId,GetListBlockRequestDto requestDto) {
        List<GetListBlocksResponseDto> list =
                blockUserRepository.getListBlocks(currentUserId, requestDto.getIndex(), requestDto.getCount());
        return new ServerResponseDto(ResponseCase.OK,list) ;
    }
    public ServerResponseDto setBlock(Long currentUserId,Long userId,Integer type) {
        if(type == 0) {
            blockUserRepository.deleteByUserBlockedIdAndUserPostId(userId,currentUserId);
        } else {
            Boolean isBlock = blockUserRepository.existsByUserBlockedIdAndUserPostId(userId,currentUserId);
            if(Boolean.FALSE.equals(isBlock)) {
                BlockUserEntity blockUserEntity = new BlockUserEntity();
                blockUserEntity.setUserBlockedId(userId);
                blockUserEntity.setUserPostId(currentUserId);
                blockUserEntity.setCreatedDate(new Date());
                blockUserRepository.save(blockUserEntity);
            }
        }

        return new ServerResponseDto(ResponseCase.OK);
    }
}
