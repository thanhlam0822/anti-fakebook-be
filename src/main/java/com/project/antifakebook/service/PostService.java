package com.project.antifakebook.service;


import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.post.*;
import com.project.antifakebook.dto.rate.GetRateResponseDto;
import com.project.antifakebook.dto.rate.SetMarkCommentRequestDto;

import com.project.antifakebook.entity.*;


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
    private final PostReportRepository postReportRepository;
    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       OldVersionOfPostRepository oldVersionOfPostRepository,
                       PostReactRepository postReactRepository,PostRateRepository postRateRepository,
                       PostImageRepository postImageRepository,PostVideoRepository postVideoRepository,
                       CategoryRepository categoryRepository,
                       BlockUserRepository blockUserRepository,
                       PostReportRepository postReportRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postReactRepository = postReactRepository;
        this.postRateRepository = postRateRepository;
        this.postImageRepository = postImageRepository;
        this.postVideoRepository = postVideoRepository;
        this.oldVersionOfPostRepository = oldVersionOfPostRepository;
        this.categoryRepository = categoryRepository;
        this.blockUserRepository = blockUserRepository;
        this.postReportRepository = postReportRepository;
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
            if (Objects.equals(postEntity.getBannedStatus(), "1")
                    || Objects.equals(postEntity.getBannedStatus(), "3")) {
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
                        postEntity.getBannedStatus(),
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
                    !Objects.equals(postEntity.getBannedStatus(), "1");
        }
    }
    public Boolean canMark(Long userId,Long postId) {
        return !isMark(userId, postId);
    }
    public Boolean canRate(Long userId,Long postId) {
        return !isRate(userId,postId);
    }
//    public ServerResponseDto editPost() {
//        return null;
//    }
//    public void editPostImage(Long postId,MultipartFile[] files,List<Integer> imageSort ) {
//        AtomicInteger i = new AtomicInteger();
//        List<PostImageEntity> fileEntities = new ArrayList<>();
//        Arrays.asList(files).forEach(file -> {
//            String url = uploadDirForPostImage + file.getOriginalFilename();
//            PostImageEntity fileEntity = new PostImageEntity(file.getOriginalFilename(), postId,url, imageSort.get(i.get()));
//            fileEntities.add(fileEntity);
//            i.getAndIncrement();
//            try {
//                FileUploadUtil.saveFile(uploadDirForPostImage, file.getOriginalFilename(), file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        postImageRepository.saveAll(fileEntities);
//    }
    public ServerResponseDto deletePost(Long postId,Long userId) {
        ServerResponseDto serverResponseDto;
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(postEntity.isPresent()) {
            if(isUserHasEnoughCoins(userId)) {
                postRepository.deleteById(postId);
                userRepository.minusUserFees(userId);
                serverResponseDto = new ServerResponseDto(ResponseCase.OK);
            } else {
                serverResponseDto = new ServerResponseDto(ResponseCase.NOT_ENOUGH_COINS);
            }
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.POST_IS_NOT_EXISTED);
        }
        return serverResponseDto;
    }
    public ServerResponseDto reportPost(Long userId,PostReportRequestDto requestDto) {
        ServerResponseDto serverResponseDto;
        Optional<PostEntity> postEntity = postRepository.findById(requestDto.getPostId());
        if(postEntity.isPresent()) {
            PostReportEntity reportEntity = new PostReportEntity(requestDto,userId);
            postReportRepository.save(reportEntity);
            serverResponseDto = new ServerResponseDto(ResponseCase.OK);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.POST_IS_NOT_EXISTED);
        }
        return serverResponseDto;
    }
    public ServerResponseDto feel(Long userId,GetPostFeelRequestDto requestDto) {
        ReactEntity reactEntity = null;
        ServerResponseDto serverResponseDto;
        Long postId = requestDto.getPostId();
        Integer typeReact = requestDto.getTypeReact();
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(postEntity.isPresent()) {
            deleteFeelIfExist(postId,typeReact,userId);
            if(typeReact == 0) {
                reactEntity = new ReactEntity(postId,ReactType.DISAPPOINTED,userId);
            } else if(typeReact == 1) {
                reactEntity = new ReactEntity(postId,ReactType.KUDOS,userId);
            }
            postReactRepository.save(Objects.requireNonNull(reactEntity));
            GetPostFeelResponseDto responseDto= new GetPostFeelResponseDto(countReactOfPost(postId,ReactType.DISAPPOINTED),
                    countReactOfPost(postId,ReactType.KUDOS));
            serverResponseDto = new ServerResponseDto(ResponseCase.OK,responseDto);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.POST_IS_NOT_EXISTED);
        }
        return serverResponseDto;
    }
    public void deleteFeelIfExist(Long postId,Integer typeReact,Long userId) {
        ReactEntity reactEntity = null;
        if(typeReact == 0 ) {
            reactEntity = postReactRepository.findByPostIdAndAndReactTypeAndUserId
                    (postId,ReactType.DISAPPOINTED,userId);
        } else if(typeReact == 1) {
            reactEntity = postReactRepository.findByPostIdAndAndReactTypeAndUserId
                    (postId,ReactType.KUDOS,userId);
        }
        if(reactEntity != null) {
            postReactRepository.deleteById(reactEntity.getId());
        }
    }
    public ServerResponseDto getMarkComment(GetMarkCommentRequestDto requestDto,Long currentUserId) {
        GetMarkResponseDto responseDto ;
        List<GetMarkResponseDto> responseDtos = new ArrayList<>();
        List<GetRateResponseDto> rateEntities =
                postRateRepository.findByPostId(requestDto.getPostId(),requestDto.getIndex(),requestDto.getCount());
        for(GetRateResponseDto entity : rateEntities) {
            responseDto = new GetMarkResponseDto();
            responseDto.setId(entity.getId());
            responseDto.setMarkContent(entity.getMarkContent());
            responseDto.setTypeOfMark(entity.getTypeOfMark());
            responseDto.setIsBlock(isAuthorBlockCurrentUser(currentUserId,entity.getUserId()));
            UserEntity user = userRepository.findById(entity.getUserId()).orElse(null);
            assert user != null;
            MarkPosterResponseDto poster = new MarkPosterResponseDto(user.getId(),user.getName(),user.getAvatarLink());
            responseDto.setPoster(poster);
            List<GetMarkCommentResponseDto> markCommentResponseDtos = new ArrayList<>();
            List<GetRateResponseDto> comments = postRateRepository.getByPostIdAndParentId(requestDto.getPostId(), entity.getId(),currentUserId);
            for(GetRateResponseDto commentEntity: comments) {
                GetMarkCommentResponseDto commentResponseDto = new GetMarkCommentResponseDto();
                commentResponseDto.setContent(commentEntity.getMarkContent());
                commentResponseDto.setCreatedDate(commentEntity.getCreatedDate());
                UserEntity userEntity = userRepository.findById(commentEntity.getUserId()).orElse(null);
                assert userEntity != null;
                MarkPosterResponseDto poster2 = new MarkPosterResponseDto(userEntity.getId(),userEntity.getName(),userEntity.getAvatarLink());
                commentResponseDto.setPoster(poster2);
                markCommentResponseDtos.add(commentResponseDto);
            }
            responseDto.setComments(markCommentResponseDtos);
            responseDtos.add(responseDto);
        }
      return new ServerResponseDto(ResponseCase.OK,responseDtos);
    }
    public ServerResponseDto setMarkComment(SetMarkCommentRequestDto requestDto,Long currentUserId) {
        GetMarkResponseDto responseDto = new GetMarkResponseDto();
        Optional<PostEntity> postEntity = postRepository.findById(requestDto.getId());
        if(postEntity.isPresent()) {
            UserEntity user = userRepository.findById(currentUserId).orElse(null);
            requestDto.setCurrentUserId(currentUserId);
            RateEntity rateEntity = new RateEntity(requestDto);
            if(requestDto.getMarkType() != null) {
                if(requestDto.getMarkType() == 0) {
                    rateEntity.setRateType(RateType.FAKE);
                } else if (requestDto.getMarkType() == 1) {
                    rateEntity.setRateType(RateType.TRUST);
                }
            } else {
                rateEntity.setRateType(null);
            }
            postRateRepository.save(rateEntity);
            responseDto.setId(rateEntity.getId());
            responseDto.setMarkContent(rateEntity.getContent());
            if(rateEntity.getRateType() != null) {
                responseDto.setTypeOfMark(rateEntity.getRateType().toString());
            } else {
                responseDto.setTypeOfMark(null);
            }
            assert user != null;
            MarkPosterResponseDto poster = new MarkPosterResponseDto(currentUserId,
                    user.getName(),user.getAvatarLink());
            responseDto.setPoster(poster);
            List<GetRateResponseDto> comments = postRateRepository.getCommentAfterSetMark(
                    requestDto.getId(),requestDto.getIndex(),requestDto.getCount());
            List<GetMarkCommentResponseDto> markCommentResponseDtos = new ArrayList<>();
            for(GetRateResponseDto comment : comments) {
                GetMarkCommentResponseDto commentResponseDto = new GetMarkCommentResponseDto();
                commentResponseDto.setContent(comment.getMarkContent());
                commentResponseDto.setCreatedDate(comment.getCreatedDate());
                UserEntity userComment = userRepository.findById(comment.getUserId()).orElse(null);
                assert userComment != null;
                MarkPosterResponseDto poster2 = new MarkPosterResponseDto(
                        userComment.getId(),userComment.getName(),userComment.getAvatarLink());
                commentResponseDto.setPoster(poster2);
                markCommentResponseDtos.add(commentResponseDto);
            }
            responseDto.setComments(markCommentResponseDtos);
        }
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
    public ServerResponseDto searchPosts(SearchPostsRequestDto requestDto,Long userId) {
        requestDto.setUserId(userId);
        List<SearchPostsResponseDto> responseDtos = new ArrayList<>();
        List<PostEntity> postEntities = postRepository.searchPosts
                (requestDto.getKeyword(), requestDto.getIndex(),requestDto.getCount());
        for(PostEntity entity : postEntities) {
            UserEntity postUser = userRepository.findById(entity.getUserId()).orElse(null);
            assert postUser != null;
            SearchPostsAuthorResponseDto author = new SearchPostsAuthorResponseDto(postUser.getId(),postUser.getName(),postUser.getAvatarLink());
            SearchPostsResponseDto responseDto = new SearchPostsResponseDto(
                    entity.getId(),
                    entity.getName(),
                    getImageOfPostDto(entity.getId()),
                    getVideoOfPostDto(entity.getId()),
                    postReactRepository.totalFeelOfPost(entity.getId()),
                    postRateRepository.totalMarkCommentOfPost(entity.getId()),
                    postReactRepository.existsByPostIdAndUserId(entity.getId(), requestDto.getUserId()),
                    author,
                    entity.getDescribed()
                    );
            responseDtos.add(responseDto);
        }
        return new ServerResponseDto(ResponseCase.OK,responseDtos);
    }
    public GetSinglePostAuthorResponseDto getAuthorOfPost(Long userId) {
        GetSinglePostAuthorResponseDto authorResponse = null;
        Optional<UserEntity> author = userRepository.findById(userId);
        if(author.isPresent()) {
            UserEntity authorEntity = author.get();
            authorResponse =
                    new GetSinglePostAuthorResponseDto
                            (authorEntity.getId(),authorEntity.getName(),authorEntity.getAvatarLink());
        }
        return authorResponse;
    }
    public ServerResponseDto getListPosts(Long currentUserId,GetListPostsRequestDto requestDto) {
        GetListPostsResponseDto responseDto = new GetListPostsResponseDto();
        List<GetSinglePostResponseDto> singlePostList = new ArrayList<>();
        boolean inCampaign = requestDto.getInCampaign() == 1;
        List<Long> postIds = postRepository
                .getListIdsOfPost(inCampaign,
                        requestDto.getCampaignId(),
                        requestDto.getLatitude(),
                        requestDto.getLongitude(),
                        requestDto.getLastId(),
                        requestDto.getIndex(),
                        requestDto.getCount());
        if(!postIds.isEmpty()) {
           List<PostEntity> postEntities = postRepository.findByIdIn(postIds);
           for(PostEntity postEntity : postEntities) {
                GetSinglePostResponseDto singlePost = new GetSinglePostResponseDto();
                singlePost.setId(postEntity.getId());
                singlePost.setName(postEntity.getName());
                singlePost.setImage(getImageOfPostDto(postEntity.getId()));
                singlePost.setVideo(getVideoOfPostDto(postEntity.getId()));
                singlePost.setDescribed(postEntity.getDescribed());
                singlePost.setCreated(postEntity.getCreatedDate());
                singlePost.setFeel(postReactRepository.totalFeelOfPost(postEntity.getId()));
                singlePost.setCommentMark(postRateRepository.totalMarkCommentOfPost(postEntity.getId()));
                singlePost.setIsFelt(isRate(currentUserId, postEntity.getId()));
                singlePost.setIsBlocked(isAuthorBlockCurrentUser(currentUserId,postEntity.getUserId()));
                singlePost.setCanEdit(canEdit(currentUserId,postEntity));
                singlePost.setBanned(postEntity.getBannedStatus());
                singlePost.setStatus(postEntity.getStatus().name());
                singlePost.setAuthor(getAuthorOfPost(postEntity.getUserId()));
                singlePostList.add(singlePost);
           }
        }
        responseDto.setPost(singlePostList);
        responseDto.setNewItems(postIds.size());
        responseDto.setLastId(requestDto.getLastId());
        return new ServerResponseDto(ResponseCase.OK,responseDto);
    }
}
