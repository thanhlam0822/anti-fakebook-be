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

    public AccountLoginResponseDto(Long id, String username, String token, String avatarLink, Integer activeStatus, Integer coins) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.avatarLink = avatarLink;
        this.activeStatus = activeStatus;
        this.coins = coins;
    }
}
