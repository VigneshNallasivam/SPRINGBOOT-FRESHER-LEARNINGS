package com.java.basics.exception;

import com.java.basics.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = JWTSecurityException.class)
    public ErrorMessage handleErrorException(JWTSecurityException exception, WebRequest request)
    {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
    }
}
