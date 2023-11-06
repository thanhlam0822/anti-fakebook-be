package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarkPosterResponseDto {
    private Long id;
    private String name;
    private String avatar;
    public MarkPosterResponseDto(Long id,String name,String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
