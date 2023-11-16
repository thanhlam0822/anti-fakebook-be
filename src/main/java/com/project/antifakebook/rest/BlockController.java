package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.block_user.GetListBlockRequestDto;
import com.project.antifakebook.service.BlockUserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/block")
public class BlockController {
    private final BlockUserService blockUserService;

    public BlockController(BlockUserService blockUserService) {
        this.blockUserService = blockUserService;
    }
    @PostMapping("/get-list-blocks")
    public ServerResponseDto getListBlocks(@AuthenticationPrincipal CustomUserDetails currentUser,
                                           @RequestBody GetListBlockRequestDto requestDto) {
        return blockUserService.getListBlocks(currentUser.getUserId(),requestDto);
    }
}
