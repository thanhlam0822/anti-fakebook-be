package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.*;
import com.project.antifakebook.entity.*;

import com.project.antifakebook.enums.BannedStatus;
import com.project.antifakebook.enums.RateType;
import com.project.antifakebook.enums.ReactType;
import com.project.antifakebook.repository.*;

import com.project.antifakebook.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class PostService {
    private static final String uploadDirForPostImage = "src/main/resources/post_image/";
    private static final String uploadDirForPostVideo= "src/main/resources/post_video/";
    private final PostRepository postRepository;
    private final OldVersionOfPostRepository oldVersionOfPostRepository;
    private final UserRepository userRepository;
    private final PostReactRepository postReactRepository;
    private final PostRateRepository postRateRepository;
    private final PostImageRepository postImageRepository;
    private final PostVideoRepository postVideoRepository;
    private final CategoryRepository categoryRepository;
    private final BlockUserRepository blockUserRepository;
    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       OldVersionOfPostRepository oldVersionOfPostRepository,
                       PostReactRepository postReactRepository,PostRateRepository postRateRepository,
                       PostImageRepository postImageRepository,PostVideoRepository postVideoRepository,
                       CategoryRepository categoryRepository,
                       BlockUserRepository blockUserRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postReactRepository = postReactRepository;
        this.postRateRepository = postRateRepository;
        this.postImageRepository = postImageRepository;
        this.postVideoRepository = postVideoRepository;
        this.oldVersionOfPostRepository = oldVersionOfPostRepository;
        this.categoryRepository = categoryRepository;
        this.blockUserRepository = blockUserRepository;
    }

    public boolean isUserHasEnoughCoins(Long userId) {
        return userRepository.getCoinsOfUser(userId) >= 4;
    }

    public ServerResponseDto addPost(Long currentUserId, MultipartFile[] files, SavePostRequestDto requestDto) {
        Set<String> stringSet = FileUploadUtil.fileTypeSet(files);
        if (isUserHasEnoughCoins(currentUserId) && stringSet.size() == 1) {
            requestDto.setCurrentUserId(currentUserId);
            PostEntity postEntity = new PostEntity(requestDto);
            postRepository.save(postEntity);
            addFileToPost(stringSet,files,postEntity.getId(),requestDto.getThumbnail());
            userRepository.minusUserFees(currentUserId);
            SavePostResponseDto responseDto = new SavePostResponseDto(postEntity.getId(), postEntity.getUrl(), userRepository.getCoinsOfUser(currentUserId));
            return new ServerResponseDto(ResponseCase.OK, responseDto);
        } else if(stringSet.size() > 1) {
            return new ServerResponseDto(ResponseCase.INVALID_FILE_UPLOAD);
        } else {
            return new ServerResponseDto(ResponseCase.NOT_ENOUGH_COINS);
        }
    }
    public void addFileToPost(Set<String> stringSet,MultipartFile[] files, Long postId,String thumbnail) {
        if(stringSet.contains("Image")) {
            addImageFileToPost(files,postId);
        } else if(stringSet.contains("Video")) {
            addVideoFileToPost(files,postId,thumbnail);
        }
    }
    public void addImageFileToPost(MultipartFile[] files, Long postId) {
        AtomicInteger imageIndex = new AtomicInteger();
        List<PostImageEntity> fileEntities = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            String url = uploadDirForPostImage + file.getOriginalFilename();
            PostImageEntity fileEntity = new PostImageEntity(file.getOriginalFilename(), postId,url, imageIndex.get());
            fileEntities.add(fileEntity);
            imageIndex.getAndIncrement();
            try {
                FileUploadUtil.saveFile(uploadDirForPostImage, file.getOriginalFilename(), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postImageRepository.saveAll(fileEntities);
    }
    public void addVideoFileToPost(MultipartFile[] files,Long postId,String thumbnail) {
        List<PostVideoEntity> fileEntities = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            String url = uploadDirForPostVideo + file.getOriginalFilename();
            PostVideoEntity fileEntity = new PostVideoEntity(file.getOriginalFilename(), postId,thumbnail,url);
            fileEntities.add(fileEntity);
            try {
                FileUploadUtil.saveFile(uploadDirForPostVideo, file.getOriginalFilename(), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postVideoRepository.saveAll(fileEntities);
    }

    public ServerResponseDto getPost(Long userId, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElse(null);
        GetPostResponseDto responseDto = null;
        if (postEntity != null) {
            if (postEntity.getBannedStatus().getCode().equals(BannedStatus.LOCKED.getCode())
                    || postEntity.getBannedStatus().getCode().equals(BannedStatus.BANNED_IN_SOME_COUNTRY.getCode())) {
                return new ServerResponseDto(ResponseCase.POST_IS_NOT_EXISTED);
            } else if (Boolean.TRUE.equals(isAuthorBlockCurrentUser(userId, postEntity.getUserId()))) {
                responseDto = new GetPostResponseDto(true);
            } else {
                responseDto = getUnlockPost(postEntity, userId, postId);
            }
        }
        return new ServerResponseDto(ResponseCase.OK, responseDto);
    }
    public GetPostResponseDto getUnlockPost(PostEntity postEntity,Long userId,Long postId) {
        return new GetPostResponseDto
                (       postEntity,
                        countReactOfPost(postId,ReactType.KUDOS),
                        countReactOfPost(postId,ReactType.DISAPPOINTED),
                        countRateOfPost(postId,RateType.FAKE),
                        countRateOfPost(postId,RateType.TRUST),
                        isRate(userId,postId),
                        isMark(userId,postId),
                        getImageOfPostDto(postId),
                        getVideoOfPostDto(postId),
                        getAuthorInformationOfPost(postEntity.getUserId(),postId),
                        getCategoryOfPost(postId),
                        postEntity.getPostState(),
                        isAuthorBlockCurrentUser(userId,postEntity.getUserId()),
                        canEdit(userId,postEntity),
                        postEntity.getBannedStatus().getCode(),
                        canMark(userId,postId),
                        canRate(userId,postId)
                );
    }
    public Integer countReactOfPost(Long postId, ReactType reactType) {
        return postReactRepository.countReactOfPost(postId,reactType);
    }
    public Integer countRateOfPost(Long postId, RateType rateType) {
        return postRateRepository.countRateOfPost(postId,rateType);
    }
    public Boolean isRate(Long userId,Long postId) {
        return postReactRepository.isRate(userId,postId) > 0;
    }
    public Boolean isMark(Long userId,Long postId) {
        return postRateRepository.isMark(userId,postId) > 0;
    }
    public List<GetPostImageResponseDto> getImageOfPostDto(Long postId) {
        List<PostImageEntity> postImageEntities = postImageRepository.findByPostIdOrderByImageIndexAsc(postId);
        List<GetPostImageResponseDto> dtos = new ArrayList<>();
        for(PostImageEntity entity : postImageEntities) {
            GetPostImageResponseDto dto = new GetPostImageResponseDto(entity.getId(), entity.getUrl());
            dtos.add(dto);
        }
        return dtos;
    }
    public List<GetPostVideoResponseDto> getVideoOfPostDto(Long postId) {
        List<PostVideoEntity> postVideoEntities = postVideoRepository.findByPostId(postId);
        List<GetPostVideoResponseDto> dtos = new ArrayList<>();
        for(PostVideoEntity entity : postVideoEntities) {
            GetPostVideoResponseDto dto = new GetPostVideoResponseDto(entity.getId(),
                    entity.getUrl(),entity.getThumbnail());
            dtos.add(dto);
        }
        return dtos;
    }

    public GetPostAuthorResponseDto getAuthorInformationOfPost(Long userId,Long postId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<Long> oldVersionIds = oldVersionOfPostRepository.getOldVersionOfCurrentPost(postId);
        return new GetPostAuthorResponseDto(userId,
                Objects.requireNonNull(user).getName(),
                user.getAvatarLink(),
                user.getCoins(),
                oldVersionIds);
    }
    public List<GetCategoryOfPostDto> getCategoryOfPost(Long postId) {
        List<GetCategoryOfPostDto> categoryOfPostDtos = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.getCategoryOfPost(postId);
        categoryEntities.forEach(entity -> {
            GetCategoryOfPostDto dto = new GetCategoryOfPostDto(entity);
            categoryOfPostDtos.add(dto);
        } );
        return categoryOfPostDtos;
    }
    public Boolean isAuthorBlockCurrentUser(Long currentUserId,Long userPostId) {
        return blockUserRepository.existsByUserBlockedIdAndUserPostId(currentUserId,userPostId);
    }
    public boolean canEdit(Long currentUserId,PostEntity postEntity) {
        if(postEntity.getBannedStatus() == null) {
            return true;
        } else {
            return postEntity.getUserId().equals(currentUserId) &&
                    !Objects.equals(postEntity.getBannedStatus().getCode(), BannedStatus.LOCKED.getCode());
        }
    }
    public Boolean canMark(Long userId,Long postId) {
        return !isMark(userId, postId);
    }
    public Boolean canRate(Long userId,Long postId) {
        return !isRate(userId,postId);
    }
    public ServerResponseDto editPost() {
        return null;
    }
    public void editPostImage(Long postId,MultipartFile[] files,List<Integer> imageSort ) {
        AtomicInteger i = new AtomicInteger();
        List<PostImageEntity> fileEntities = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            String url = uploadDirForPostImage + file.getOriginalFilename();
            PostImageEntity fileEntity = new PostImageEntity(file.getOriginalFilename(), postId,url, imageSort.get(i.get()));
            fileEntities.add(fileEntity);
            i.getAndIncrement();
            try {
                FileUploadUtil.saveFile(uploadDirForPostImage, file.getOriginalFilename(), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postImageRepository.saveAll(fileEntities);
    }
}
