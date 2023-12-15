package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetMarkResponseDto {
    private String id;
    private String markContent;
    private String typeOfMark;
    private MarkPosterResponseDto poster;
    private List<GetMarkCommentResponseDto> comments;
    private String isBlock;


}
