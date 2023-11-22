package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPartnerDto {
    private Long id;
    private String username;
    private String avatar;

    public GetPartnerDto(Long id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }
}
