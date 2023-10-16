package com.project.antifakebook.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;



@Entity
@Getter
@Setter
@Table(name = "verify_token")
public class VerificationTokenEntity {

    private static final int EXPIRATION = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    @Column(unique = true)
    private Long userId;

    private Date expiryDate;
    private Date timeGetToken;

    public VerificationTokenEntity() {
        super();
    }

    public VerificationTokenEntity(final String token) {
        super();
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationTokenEntity(final String token, final Long userId) {
        super();
        this.token = token;
        this.userId = userId;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.timeGetToken = new Date();
    }



    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }



}