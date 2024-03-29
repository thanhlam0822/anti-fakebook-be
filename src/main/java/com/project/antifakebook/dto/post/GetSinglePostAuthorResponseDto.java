package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSinglePostAuthorResponseDto {
    private String id;
    private String username;
    private String avatar;

    public GetSinglePostAuthorResponseDto(Long id, String username, String avatar) {
        this.id = id.toString();
        this.username = username;
        this.avatar = avatar;
    }
}
