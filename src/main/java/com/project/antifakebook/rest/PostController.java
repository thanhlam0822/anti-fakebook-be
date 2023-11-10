package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.GetMarkCommentRequestDto;
import com.project.antifakebook.dto.post.GetPostFeelRequestDto;
import com.project.antifakebook.dto.post.PostReportRequestDto;
import com.project.antifakebook.dto.post.SavePostRequestDto;


import com.project.antifakebook.dto.rate.SetMarkCommentRequestDto;
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
    @DeleteMapping("delete-post")
    public ServerResponseDto deletePost(@AuthenticationPrincipal CustomUserDetails currentUser,
                                        @RequestParam Long postId) {
        return postService.deletePost(postId,currentUser.getUserId());
    }
    @PostMapping("report-post")
    public ServerResponseDto reportPort(@AuthenticationPrincipal CustomUserDetails currentUser,
                                        @RequestBody PostReportRequestDto requestDto) {
        return postService.reportPost(currentUser.getUserId(),requestDto);
    }
    @PostMapping("feel")
    public ServerResponseDto feel(@AuthenticationPrincipal CustomUserDetails currentUser ,
                                  @RequestBody GetPostFeelRequestDto requestDto) {
        return postService.feel(currentUser.getUserId(),requestDto);
    }
    @PostMapping("get-mark-comment")
    public ServerResponseDto getMarkComment(@AuthenticationPrincipal CustomUserDetails currentUser,
                                  @RequestBody GetMarkCommentRequestDto requestDto) {
        return postService.getMarkComment(requestDto, currentUser.getUserId());
    }
    @PostMapping("set-mark-comment")
    public ServerResponseDto setMarkComment(@AuthenticationPrincipal CustomUserDetails currentUser,
                                            @RequestBody SetMarkCommentRequestDto requestDto)  {
        return postService.setMarkComment(requestDto,currentUser.getUserId());
    }
}

