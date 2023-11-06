package com.project.antifakebook.entity;

import com.project.antifakebook.enums.ReactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "react")
public class ReactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    @Enumerated(EnumType.STRING)
    private ReactType reactType;
    private Date createdDate;

    public ReactEntity( Long postId, ReactType reactType,Long userId) {
        this.postId = postId;
        this.reactType = reactType;
        this.createdDate = new Date();
        this.userId = userId;
    }
}
