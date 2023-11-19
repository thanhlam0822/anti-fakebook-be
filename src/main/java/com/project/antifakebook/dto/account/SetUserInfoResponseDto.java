package com.project.antifakebook.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetUserInfoResponseDto {
    private String avatar;
    private String coverImage;
    private String link;
    private String city;
    private String country;

    public SetUserInfoResponseDto(String avatar, String coverImage, String link, String city, String country) {
        this.avatar = avatar;
        this.coverImage = coverImage;
        this.link = link;
        this.city = city;
        this.country = country;
    }
}
