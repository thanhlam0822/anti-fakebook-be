package com.project.antifakebook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.project.antifakebook.enums.ActiveStatusCode;
import com.project.antifakebook.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    @JsonIgnore
    private String password;
    @Column(unique = true)
    private String email;
    @Column
    private String avatarLink;
    @Column
    private String token;
    @Column
    private String session;
    @Column
    private String uuid;
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @Column
    private Date createdDate;
    @Column
    private Integer activeStatus;
    @Column
    private Integer coins;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_GUEST;
        this.uuid = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.activeStatus = ActiveStatusCode.INACTIVE.getCode();
        this.coins = 10;
    }

    public String getRole() {
        return role.toString();
    }
}
