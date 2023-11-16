package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.block_user.GetListBlockRequestDto;
import com.project.antifakebook.dto.block_user.GetListBlocksResponseDto;
import com.project.antifakebook.repository.BlockUserRepository;
import org.springframework.stereotype.Service;

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
}
