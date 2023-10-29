package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPostVideoResponseDto {
    private Long id;
    private String url;
    private String thumbnail;

    public GetPostVideoResponseDto(Long id, String url, String thumbnail) {
        this.id = id;
        this.url = url;
        this.thumbnail = thumbnail;
    }
}
