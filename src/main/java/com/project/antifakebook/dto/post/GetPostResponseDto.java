package com.project.antifakebook.dto.post;


import com.project.antifakebook.entity.PostEntity;
import com.project.antifakebook.enums.PostState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetPostResponseDto {
    private String id;
    private String name;
    private String described;
    private String createdDate;
    private String modifiedDate;
    private String kudos;
    private String disappointed;
    private String fakes;
    private String trusts;
    private String isRate;
    private String isMark;
    private List<GetPostImageResponseDto> images;
    private List<GetPostVideoResponseDto> videos;
    private GetPostAuthorResponseDto author;
    private List<GetCategoryOfPostDto> category;
    private String state;
    private String isBlock;
    private String canEdit;
    private String bannedStatus;
    private String canMark;
    private String canRate;
    public GetPostResponseDto(PostEntity postEntity,Integer kudos,Integer disappointed,Integer fakes,Integer trusts,
                              Boolean isRate,Boolean isMark,
                              List<GetPostImageResponseDto> images,
                              List<GetPostVideoResponseDto> videos,
                              GetPostAuthorResponseDto authorResponseDto,
                              List<GetCategoryOfPostDto> categoryOfPostDtos,
                              PostState state,
                              Boolean isBlock ,Boolean canEdit,
                              String bannedStatus,Boolean canMark,Boolean canRate) {
        this.id = postEntity.getId().toString();
        this.name = postEntity.getName();
        this.described = postEntity.getDescribed();
        if(createdDate != null) {
            this.createdDate = postEntity.getCreatedDate().toString();
        } else {
            this.createdDate = "";
        }
        if(modifiedDate != null) {
            this.modifiedDate = postEntity.getModifiedDate().toString();
        } else {
            this.modifiedDate = "";
        }
        this.kudos = kudos.toString();
        this.disappointed = disappointed.toString();
        this.fakes = fakes.toString();
        this.trusts = trusts.toString();
        this.isRate = isRate.toString();
        this.isMark = isMark.toString();
        this.images = images;
        this.videos = videos;
        this.author = authorResponseDto;
        this.category = categoryOfPostDtos;
        this.state = state.toString();
        this.isBlock = isBlock.toString();
        this.canEdit = canEdit.toString();
        this.bannedStatus = bannedStatus;
        this.canMark = canMark.toString();
        this.canRate = canRate.toString();
    }
    public GetPostResponseDto(Boolean isBlock) {
        this.isBlock = isBlock.toString();
    }
}
