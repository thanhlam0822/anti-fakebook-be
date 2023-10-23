package com.project.antifakebook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class ServerResponseDto {
    private ResponseStatus status;
    private Object data;

    public ServerResponseDto(ResponseStatus responseStatus) {
        this.status = responseStatus;
    }

    public ServerResponseDto(ResponseStatus responseStatus, Object data) {
        this.status = responseStatus;
        this.data = data;
    }


}
