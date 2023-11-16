package com.project.antifakebook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "suggested_friend")
public class SuggestedFriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long friendId;
    private Date createdDate;

    @Override
    public String toString() {
        return "SuggestedFriendEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                ", createdDate=" + createdDate +
                '}';
    }
}
