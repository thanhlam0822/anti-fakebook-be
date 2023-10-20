package com.project.antifakebook.dto.account;

import com.project.antifakebook.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ChangeInfoAfterSignUpResponseDto {
    private Long id;
    private String username;
    private String email;
    private Date created;
    private String avatar;
    public ChangeInfoAfterSignUpResponseDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getName();
        this.email = userEntity.getEmail();
        this.created = userEntity.getCreatedDate();
        this.avatar = userEntity.getAvatarLink();
    }
}
