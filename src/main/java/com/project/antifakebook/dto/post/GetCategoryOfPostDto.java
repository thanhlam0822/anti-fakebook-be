package com.project.antifakebook.dto.post;

import com.project.antifakebook.entity.CategoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryOfPostDto {
    private Long id;
    private String name;
    public GetCategoryOfPostDto(CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
