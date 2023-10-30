package com.project.antifakebook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post_image")
public class PostImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long postId;
    private Date dateUpload;
    private String url;
    private Integer imageIndex;
    public PostImageEntity(String name,Long postId,String url,Integer imageIndex) {
        this.name = name;
        this.postId = postId;
        this.dateUpload = new Date();
        this.url = url;
        this.imageIndex = imageIndex;
    }
}
