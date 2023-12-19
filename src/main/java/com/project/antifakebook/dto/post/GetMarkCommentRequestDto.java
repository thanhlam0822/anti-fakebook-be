package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetMarkCommentRequestDto {
    private Long id;
    private Integer index;
    private Integer count;
}
