package com.project.antifakebook.dto.verify_code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckVerifyCodeResponseDto {
    private String id;
    private String active;

    public CheckVerifyCodeResponseDto(String id, String active) {
        this.id = id;
        this.active = active;
    }
}
