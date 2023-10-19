package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeInfoAfterSignUpRequestDto {
    private String token;
    private String username;
    private String avatar;
}
