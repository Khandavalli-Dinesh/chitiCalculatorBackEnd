package com.galvan.chiti.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.galvan.chiti.auxilliaries.NoPrivilageException;

@ControllerAdvice
public class HandlingException {

	@ExceptionHandler(NoPrivilageException.class)
    public ResponseEntity<Object> noPrivilageException(NoPrivilageException exception, WebRequest webRequest) {
		
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(403);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.FORBIDDEN);
        
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRecordNotFoundException(Exception exception, WebRequest webRequest) {
		
		
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(400);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.NOT_FOUND);
        
    }
	
	
}
