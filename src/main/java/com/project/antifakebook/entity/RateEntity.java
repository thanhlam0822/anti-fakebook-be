package com.project.antifakebook.entity;

import com.project.antifakebook.dto.rate.SetMarkCommentRequestDto;
import com.project.antifakebook.enums.RateType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rate")
public class RateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Long parentId;
    @Enumerated(EnumType.STRING)
    private RateType rateType;
    private Date createdDate;
    public RateEntity(SetMarkCommentRequestDto requestDto) {
        this.postId = requestDto.getId();
        this.content = requestDto.getContent();
        this.parentId = requestDto.getMarkId();
        this.createdDate = new Date();
        this.userId = requestDto.getCurrentUserId();
    }

}
