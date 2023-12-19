package com.project.antifakebook.dto.verify_code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetVerifyCodeRequestDto {
    private String email;
}
