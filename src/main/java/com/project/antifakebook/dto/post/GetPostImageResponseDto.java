package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPostImageResponseDto {
    private String id;
    private String url;
    public GetPostImageResponseDto(Long id,String url) {
        this.id = id.toString();
        this.url = url;
    }
}
