package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetSinglePostResponseDto {
    private Long id;
    private String name;
    private List<GetPostImageResponseDto> image;
    private List<GetPostVideoResponseDto> video;
    private String described;
    private Date created;
    private Integer feel;
    private Integer commentMark;
    private Boolean isFelt;
    private Boolean isBlocked;
    private Boolean canEdit;
    private String banned;
    private String status;
    private GetSinglePostAuthorResponseDto author;
}
