package com.project.antifakebook.dto.saved_search;

import com.project.antifakebook.entity.SavedSearchEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSavedSearchResponseDto {
    private String id;
    private String keyword;
    private String created;
    public GetSavedSearchResponseDto(SavedSearchEntity entity) {
        this.id = entity.getId().toString();
        this.keyword = entity.getKeyword();
        this.created = entity.getCreatedDate().toString();
    }
}
