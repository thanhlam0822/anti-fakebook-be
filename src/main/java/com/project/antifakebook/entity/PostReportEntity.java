package com.project.antifakebook.entity;

import com.project.antifakebook.dto.post.PostReportRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post_report")
public class PostReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private String subject;
    private String detail;
    private Long userId;
    private Date createdDate;
    public PostReportEntity(PostReportRequestDto requestDto,Long userId) {
        this.postId = requestDto.getPostId();
        this.subject = requestDto.getSubject();
        this.detail = requestDto.getDetail();
        this.userId = userId;
        this.createdDate = new Date();
    }
}
