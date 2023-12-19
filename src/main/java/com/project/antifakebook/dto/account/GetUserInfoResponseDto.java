package com.project.antifakebook.dto.account;

import com.project.antifakebook.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class GetUserInfoResponseDto {
    private String id;
    private String username;
    private String description;
    private String avatar;
    private String coverImage;
    private String link;
    private String address;
    private String city;
    private String country;
    private String listing;
    private String isFriend;
    private String online;
    private String coins;
    public GetUserInfoResponseDto(UserEntity userEntity,
                                  Integer listing,
                                  Integer isFriend,
                                  Integer online,
                                  Integer coins) {
        this.id = userEntity.getId().toString();
        this.username = userEntity.getName();
        this.description = userEntity.getDescription();
        this.avatar = userEntity.getAvatarLink();
        this.coverImage = userEntity.getCoverImage();
        this.link = userEntity.getLink();
        this.address = userEntity.getAddress();
        this.city = userEntity.getCity();
        this.country = userEntity.getCountry();
        this.listing = listing.toString();
        this.isFriend = isFriend.toString();
        this.online = online.toString();
        this.coins = coins.toString();
    }
}
