package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.SavePostRequestDto;
import com.project.antifakebook.dto.post.SavePostResponseDto;
import com.project.antifakebook.entity.PostEntity;
import com.project.antifakebook.entity.PostFileEntity;
import com.project.antifakebook.enums.Status;
import com.project.antifakebook.repository.PostRepository;

import com.project.antifakebook.repository.PostFileRepository;
import com.project.antifakebook.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PostService {
    private static final String uploadDir = "src/main/resources/post_file/";
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    public PostService(PostRepository postRepository, PostFileRepository postFileRepository) {
        this.postRepository = postRepository;
        this.postFileRepository = postFileRepository;
    }
    public ServerResponseDto addPost(Long currentUserId, Integer coins, MultipartFile[] files, SavePostRequestDto requestDto) {
        List<PostFileEntity> fileEntities = new ArrayList<>();
        requestDto.setCurrentUserId(currentUserId);
        requestDto.setStatus(Status.ANGRY);
        PostEntity postEntity = new PostEntity(requestDto);
        postRepository.save(postEntity);
        Arrays.asList(files).forEach(file -> {
            PostFileEntity fileEntity = new PostFileEntity(file.getOriginalFilename(),file.getContentType(),postEntity.getId());
            fileEntities.add(fileEntity);
            try {
                FileUploadUtil.saveFile(uploadDir,file.getOriginalFilename(),file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postFileRepository.saveAll(fileEntities);
        SavePostResponseDto responseDto = new SavePostResponseDto(postEntity.getId(),postEntity.getUrl(),coins);
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
}
