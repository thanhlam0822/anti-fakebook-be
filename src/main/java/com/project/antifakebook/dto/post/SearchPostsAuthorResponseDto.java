package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostsAuthorResponseDto {
    private Long id;
    private String username;
    private String avatar;

    public SearchPostsAuthorResponseDto(Long id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }
}
