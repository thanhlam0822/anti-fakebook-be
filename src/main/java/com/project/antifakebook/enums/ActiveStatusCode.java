package com.project.antifakebook.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ActiveStatusCode {
    INACTIVE(0),
    ACTIVE(1),
    WAIT_TO_CHANGE_NAME_AND_AVATAR(-1);

    private final Integer code;

}
