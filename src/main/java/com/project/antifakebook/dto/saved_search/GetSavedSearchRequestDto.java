package com.project.antifakebook.dto.saved_search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSavedSearchRequestDto {
    private Integer index;
    private Integer count;
}
