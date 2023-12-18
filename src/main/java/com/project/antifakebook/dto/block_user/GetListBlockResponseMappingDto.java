package com.project.antifakebook.dto.block_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetListBlockResponseMappingDto {
    private String id;
    private String name;
    private String avatar;

    public GetListBlockResponseMappingDto(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
