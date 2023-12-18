package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostsResponseDto {
    private String id;
    private String name;
    private List<GetPostImageResponseDto> image;
    private List<GetPostVideoResponseDto> video;
    private String feel;
    private String markComment;
    private String isFelt;
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
        this.id = id.toString();
        this.name = name;
        this.image = image;
        this.video = video;
        this.feel = feel.toString();
        this.markComment = markComment.toString();
        this.isFelt = isFelt.toString();
        this.author = author;
        this.described = described;
    }
}
