package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetUserInfoRequestDto {
    private String username;
    private String description;
    private String link;
    private String address;
    private String city;
    private String country;
}
