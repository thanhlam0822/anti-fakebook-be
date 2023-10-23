package com.project.antifakebook.entity;

import com.project.antifakebook.dto.post.SavePostRequestDto;
import com.project.antifakebook.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "post")
@Getter
@Setter
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String described;
    @Enumerated
    private Status status;
    private String url;
    public PostEntity(SavePostRequestDto requestDto) {
        this.userId = requestDto.getCurrentUserId();
        this.described = requestDto.getDescribed();
        this.status = requestDto.getStatus();
    }
}
