package com.project.antifakebook.dto.post;


import com.project.antifakebook.entity.PostEntity;
import com.project.antifakebook.enums.BannedStatus;
import com.project.antifakebook.enums.PostState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetPostResponseDto {
    private Long id;
    private String described;
    private Date createdDate;
    private Date modifiedDate;
    private Integer kudos;
    private Integer disappointed;
    private Integer fakes;
    private Integer trusts;
    private Boolean isRate;
    private Boolean isMark;
    private List<GetPostImageResponseDto> images;
    private List<GetPostVideoResponseDto> videos;
    private GetPostAuthorResponseDto author;
    private List<GetCategoryOfPostDto> category;
    private PostState state;
    private Boolean isBlock;
    private Boolean canEdit;
    private BannedStatus bannedStatus;
    private Boolean canMark;
    private Boolean canRate;
    public GetPostResponseDto(PostEntity postEntity,Integer kudos,Integer disappointed,Integer fakes,Integer trusts,
                              Boolean isRate,Boolean isMark,
                              List<GetPostImageResponseDto> images,
                              List<GetPostVideoResponseDto> videos,
                              GetPostAuthorResponseDto authorResponseDto,
                              List<GetCategoryOfPostDto> categoryOfPostDtos,
                              PostState state,
                              Boolean isBlock ,Boolean canEdit,
                              BannedStatus bannedStatus,Boolean canMark,Boolean canRate) {
        this.id = postEntity.getId();
        this.described = postEntity.getDescribed();
        this.createdDate = postEntity.getCreatedDate();
        this.modifiedDate = postEntity.getModifiedDate();
        this.kudos = kudos;
        this.disappointed = disappointed;
        this.fakes = fakes;
        this.trusts = trusts;
        this.isRate = isRate;
        this.isMark = isMark;
        this.images = images;
        this.videos = videos;
        this.author = authorResponseDto;
        this.category = categoryOfPostDtos;
        this.state = state;
        this.isBlock = isBlock;
        this.canEdit = canEdit;
        this.bannedStatus = bannedStatus;
        this.canMark = canMark;
        this.canRate = canRate;
    }
}
