package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginRequestDto {
    private String email;
    private String password;
}
