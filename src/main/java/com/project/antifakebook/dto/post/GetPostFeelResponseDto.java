package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPostFeelResponseDto {
    private String disappointed;
    private String kudos;

    public GetPostFeelResponseDto(Integer disappointed, Integer kudos) {
        this.disappointed = disappointed.toString();
        this.kudos = kudos.toString();
    }
}
