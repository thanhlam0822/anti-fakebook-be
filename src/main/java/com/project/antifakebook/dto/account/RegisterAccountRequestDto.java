package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterAccountRequestDto {
    private String email;
    private String password;

}
