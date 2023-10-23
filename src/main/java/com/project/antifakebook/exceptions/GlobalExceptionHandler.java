package com.project.antifakebook.exceptions;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtValidationException.class)
    public ServerResponseDto handleJwtValidationException() {
        return new ServerResponseDto(ResponseCase.INVALID_TOKEN);
    }
}
