package com.project.antifakebook.dto.rate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class SetMarkCommentRequestDto {
    private Long id;
    private String content;
    private Integer index;
    private Integer count;
    private Long markId;
    private Integer markType;
    private Long currentUserId;
}
