package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.SavePostRequestDto;
import com.project.antifakebook.dto.post.SavePostResponseDto;
import com.project.antifakebook.entity.PostEntity;
import com.project.antifakebook.entity.PostFileEntity;
import com.project.antifakebook.repository.PostRepository;

import com.project.antifakebook.repository.PostFileRepository;
import com.project.antifakebook.repository.UserRepository;
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
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostFileRepository postFileRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postFileRepository = postFileRepository;
        this.userRepository = userRepository;
    }

    public boolean isUserHasEnoughCoins(Long userId) {
        return userRepository.getCoinsOfUser(userId) >= 4;
    }

    public ServerResponseDto addPost(Long currentUserId, MultipartFile[] files, SavePostRequestDto requestDto) {
        if (isUserHasEnoughCoins(currentUserId)) {
            List<PostFileEntity> fileEntities = new ArrayList<>();
            requestDto.setCurrentUserId(currentUserId);
            PostEntity postEntity = new PostEntity(requestDto);
            postRepository.save(postEntity);
            Arrays.asList(files).forEach(file -> {
                PostFileEntity fileEntity = new PostFileEntity(file.getOriginalFilename(), file.getContentType(), postEntity.getId());
                fileEntities.add(fileEntity);
                try {
                    FileUploadUtil.saveFile(uploadDir, file.getOriginalFilename(), file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            postFileRepository.saveAll(fileEntities);
            userRepository.minusUserFees(currentUserId);
            SavePostResponseDto responseDto = new SavePostResponseDto(postEntity.getId(), postEntity.getUrl(), userRepository.getCoinsOfUser(currentUserId));
            return new ServerResponseDto(ResponseCase.OK, responseDto);
        } else {
            return new ServerResponseDto(ResponseCase.NOT_ENOUGH_COINS);
        }
    }
}
