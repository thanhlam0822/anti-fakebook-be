package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetListPostsResponseDto {
    private List<GetSinglePostResponseDto> post;
    private String newItems;
    private String lastId;
}
