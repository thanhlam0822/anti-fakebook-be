package com.project.antifakebook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseStatus {
    private int code;
    private String message;

    public ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseStatus(String message) {
        this.message = message;
    }
}
