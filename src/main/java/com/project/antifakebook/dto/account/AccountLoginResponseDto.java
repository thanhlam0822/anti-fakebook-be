package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginResponseDto {
    private Long id;
    private String username;
    private String token;
    private String avatarLink;
    private Integer activeStatus;
    private Integer coins;
}
