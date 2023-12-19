package com.project.antifakebook.dto.verify_code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckVerifyCodeRequestDto {
    private String email;
    private String codeVerify;
}
