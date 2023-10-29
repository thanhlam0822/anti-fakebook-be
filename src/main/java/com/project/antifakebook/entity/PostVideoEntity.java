package com.project.antifakebook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_video")
@Entity
public class PostVideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long postId;
    private Date dateUpload;
    private String thumbnail;
    private String url;
    public PostVideoEntity( String name, Long postId,String thumbnail,String url) {
        this.name = name;
        this.postId = postId;
        this.dateUpload = new Date();
        this.thumbnail = thumbnail;
        this.url = url;
    }
}
