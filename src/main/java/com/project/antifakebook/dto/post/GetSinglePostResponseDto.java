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
    private String id;
    private String name;
    private List<GetPostImageResponseDto> image;
    private List<GetPostVideoResponseDto> video;
    private String described;
    private String created;
    private String feel;
    private String commentMark;
    private String isFelt;
    private String isBlocked;
    private String canEdit;
    private String banned;
    private String status;
    private GetSinglePostAuthorResponseDto author;
}
