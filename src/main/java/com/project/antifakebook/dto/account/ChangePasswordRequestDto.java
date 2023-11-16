package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequestDto {
    private String password;
    private String newPassword;
}
