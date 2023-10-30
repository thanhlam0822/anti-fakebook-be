package com.project.antifakebook.entity;

import com.project.antifakebook.dto.post.SavePostRequestDto;
import com.project.antifakebook.enums.BannedStatus;
import com.project.antifakebook.enums.PostState;
import com.project.antifakebook.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private PostState postState;
    @Column(name = "banned_status")
    @Enumerated(EnumType.STRING)
    private BannedStatus bannedStatus;
    private String url;
    private Date modifiedDate;
    private Date createdDate;
    private Boolean isOldVersion;
    public PostEntity(SavePostRequestDto requestDto) {
        this.userId = requestDto.getCurrentUserId();
        this.described = requestDto.getDescribed();
        this.status = requestDto.getStatus();
        this.postState = requestDto.getState();
        this.createdDate = new Date();
        this.isOldVersion = false;
        this.bannedStatus = BannedStatus.NORMAL;
    }
}
