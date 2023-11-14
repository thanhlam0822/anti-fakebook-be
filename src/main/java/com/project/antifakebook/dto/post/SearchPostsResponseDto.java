package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostsResponseDto {
    private Long id;
    private String name;
    private List<GetPostImageResponseDto> image;
    private List<GetPostVideoResponseDto> video;
    private Integer feel;
    private Integer markComment;
    private Boolean isFelt;
    private SearchPostsAuthorResponseDto author;
    private String described;

    public SearchPostsResponseDto(Long id,
                                  String name,
                                  List<GetPostImageResponseDto> image,
                                  List<GetPostVideoResponseDto> video,
                                  Integer feel,
                                  Integer markComment,
                                  Boolean isFelt,
                                  SearchPostsAuthorResponseDto author,
                                  String described) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.video = video;
        this.feel = feel;
        this.markComment = markComment;
        this.isFelt = isFelt;
        this.author = author;
        this.described = described;
    }
}
