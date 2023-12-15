package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeletePostResponseDto {
    private String coins;

    public DeletePostResponseDto(String coins) {
        this.coins = coins;
    }
}
