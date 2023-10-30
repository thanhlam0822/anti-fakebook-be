package com.project.antifakebook.dto.post;

import com.project.antifakebook.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditPostResponseDto {
    private Long id;
    private String described;
    private Status status;
}
