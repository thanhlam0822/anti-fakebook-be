package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetPostAuthorResponseDto {
    private String id;
    private String name;
    private String avatar;
    private String coins;
    private List<String> oldVersionOfPostId;

    public GetPostAuthorResponseDto(Long id, String name, String avatar, Integer coins, List<String> oldVersionOfPostId) {
        this.id = id.toString();
        this.name = name;
        this.avatar = avatar;
        this.coins = coins.toString();
        this.oldVersionOfPostId = oldVersionOfPostId;
    }
}
