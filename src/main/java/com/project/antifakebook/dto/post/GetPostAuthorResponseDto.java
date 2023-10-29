package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetPostAuthorResponseDto {
    private Long id;
    private String name;
    private String avatar;
    private Integer coins;
    private List<Long> oldVersionOfPostId;

    public GetPostAuthorResponseDto(Long id, String name, String avatar, Integer coins, List<Long> oldVersionOfPostId) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.coins = coins;
        this.oldVersionOfPostId = oldVersionOfPostId;
    }
}
