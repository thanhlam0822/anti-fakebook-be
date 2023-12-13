package com.project.antifakebook.dto.post;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavePostResponseDto {
    private String id;
    private String url;
    private String coins;

    public SavePostResponseDto(Long id, String url, Integer coins) {
        this.id = id.toString();
        this.url = url;
        this.coins = coins.toString();
    }
}
