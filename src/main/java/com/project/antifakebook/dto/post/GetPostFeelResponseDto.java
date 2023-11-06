package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPostFeelResponseDto {
    private Integer disappointed;
    private Integer kudos;

    public GetPostFeelResponseDto(Integer disappointed, Integer kudos) {
        this.disappointed = disappointed;
        this.kudos = kudos;
    }
}
