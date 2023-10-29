package com.project.antifakebook.dto.post;

import com.project.antifakebook.enums.PostState;
import com.project.antifakebook.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavePostRequestDto {
    private Long currentUserId;
    private String described;
    private Status status;
    private PostState state;
    private String thumbnail;
}
