package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginResponseDto {
    private String id;
    private String username;
    private String token;
    private String avatarLink;
    private String activeStatus;
    private String coins;

    public AccountLoginResponseDto(String id, String username, String token, String avatarLink, String activeStatus, String coins) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.avatarLink = avatarLink;
        this.activeStatus = activeStatus;
        this.coins = coins;
    }
}
