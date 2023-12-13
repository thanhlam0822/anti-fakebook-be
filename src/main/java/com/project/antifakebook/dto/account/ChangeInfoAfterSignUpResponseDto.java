package com.project.antifakebook.dto.account;

import com.project.antifakebook.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangeInfoAfterSignUpResponseDto {
    private String id;
    private String username;
    private String email;
    private String created;
    private String avatar;
    public ChangeInfoAfterSignUpResponseDto(UserEntity userEntity) {
        this.id = userEntity.getId().toString();
        this.username = userEntity.getName();
        this.email = userEntity.getEmail();
        this.created = userEntity.getCreatedDate().toString();
        this.avatar = userEntity.getAvatarLink();
    }
}
