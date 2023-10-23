package com.project.antifakebook.dto.post;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavePostResponseDto {
    private Long id;
    private String url;
    private Integer coins;

    public SavePostResponseDto(Long id, String url, Integer coins) {
        this.id = id;
        this.url = url;
        this.coins = coins;
    }
}
