package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.SavePostRequestDto;


import com.project.antifakebook.service.PostService;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;





@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("add-post")
    public ServerResponseDto addPost(@RequestPart("files") MultipartFile[] files,
                                     @AuthenticationPrincipal CustomUserDetails currentUser,
                                     @RequestPart("entity") SavePostRequestDto requestDto) {
        return postService.addPost(currentUser.getUserId(), files, requestDto);
    }

    @GetMapping("get-post")
    public ServerResponseDto getPost(@AuthenticationPrincipal CustomUserDetails currentUser,
                                     @RequestParam Long id) {
        return postService.getPost(currentUser.getUserId(), id);
    }
}

