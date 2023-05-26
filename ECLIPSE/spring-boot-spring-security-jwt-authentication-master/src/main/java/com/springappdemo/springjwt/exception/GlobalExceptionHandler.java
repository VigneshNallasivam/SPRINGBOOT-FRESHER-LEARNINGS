package com.springappdemo.springjwt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.springappdemo.springjwt.payload.response.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = JWTSecurityException.class)
	public ErrorMessage handleErrorException(JWTSecurityException exception, WebRequest request) {
		return new ErrorMessage(
				HttpStatus.FORBIDDEN.value(),
		        new Date(),
		        exception.getMessage(),
		        request.getDescription(false));
	}
}
