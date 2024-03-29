package com.project.antifakebook.dto.conversation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSenderDto extends GetPartnerDto {
    public GetSenderDto(Long id, String username, String avatar) {
        super(id, username, avatar);
    }
}
