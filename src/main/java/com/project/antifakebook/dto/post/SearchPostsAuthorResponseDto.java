package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostsAuthorResponseDto {
    private String id;
    private String username;
    private String avatar;

    public SearchPostsAuthorResponseDto(Long id, String username, String avatar) {
        this.id = id.toString();
        this.username = username;
        this.avatar = avatar;
    }
}
