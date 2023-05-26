package com.java.basics.exception;

import com.java.basics.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AopExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotvalidException(MethodArgumentNotValidException e)
    {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<String> errMsg = errors
                .stream()
                .map(ObjectError-> ObjectError.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseDto = new ResponseDTO("Occurence of Exception in Usage of REST",errMsg.toString());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AopException.class)
    public ResponseEntity<ResponseDTO> handleEmployeeException(AopException e)
    {
        ResponseDTO responseDto = new ResponseDTO("Occurence of Exception in Usage of REST",e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_GATEWAY);
    }


}
