package com.java.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class JoinException extends RuntimeException
{
	public JoinException(String message)
	{
		super(message);
	}

}
