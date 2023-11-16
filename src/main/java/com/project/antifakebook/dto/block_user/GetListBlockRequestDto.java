package com.project.antifakebook.dto.block_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GetListBlockRequestDto {
    private Integer index;
    private Integer count;
}
