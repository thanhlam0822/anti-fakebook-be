package com.project.antifakebook.exceptions;



import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);

    }

}