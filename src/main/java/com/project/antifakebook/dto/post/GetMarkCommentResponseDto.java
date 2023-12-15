package com.project.antifakebook.dto.post;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
public class GetMarkCommentResponseDto {
    private String content;
    private String createdDate;
    private MarkPosterResponseDto poster;
}
