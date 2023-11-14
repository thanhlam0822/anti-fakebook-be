package com.project.antifakebook.dto.saved_search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelSavedSearchRequestDto {
    private Long searchId;
    private Boolean all;
}
