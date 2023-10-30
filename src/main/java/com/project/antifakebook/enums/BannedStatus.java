package com.project.antifakebook.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BannedStatus {
    NORMAL(0),
    LOCKED(1),
    BANNED_IN_SOME_COUNTRY(2),
    BANNED_CONTENT(3);
    private final Integer code;
}
