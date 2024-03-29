package com.project.antifakebook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "request_friend")
public class RequestFriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long friendId;
    private Boolean isAccept;
    private Date createdDate;
    private Date editDate;

    public RequestFriendEntity(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
        this.isAccept = false;
        this.createdDate = new Date();
    }
}
