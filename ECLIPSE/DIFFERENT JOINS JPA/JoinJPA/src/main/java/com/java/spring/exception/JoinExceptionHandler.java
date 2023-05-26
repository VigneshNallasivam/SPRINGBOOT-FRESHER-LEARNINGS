package com.java.spring.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.java.spring.dto.ResponseDto;

@ControllerAdvice
public class JoinExceptionHandler 
{
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ResponseDto> handleMethodArgumentNotvalidException(MethodArgumentNotValidException e)
	    {
	        List<ObjectError> errors = e.getBindingResult().getAllErrors();
	        List<String> errMsg = errors
	                .stream()
	                .map(ObjectError-> ObjectError.getDefaultMessage())
	                .collect(Collectors.toList());
	        ResponseDto responseDto = new ResponseDto("Occurence of Exception in Usage of REST",errMsg.toString());
	        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(JoinException.class)
	    public ResponseEntity<ResponseDto> handleEmployeeException(JoinException e)
	    {
	        ResponseDto responseDto = new ResponseDto("Occurence of Exception in Usage of REST",e.getMessage());
	        return new ResponseEntity<>(responseDto,HttpStatus.BAD_GATEWAY);
	    }

}
