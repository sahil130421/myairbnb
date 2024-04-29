package com.airbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> responseEntity (ResourceNotFound resourceNotFound,WebRequest webRequest ){
        ErrorDetails errorDetails = new ErrorDetails(resourceNotFound.getMessage(),webRequest.getDescription(true),new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> ReviewAlreadyExist (AlreadyExistException alreadyExistException,WebRequest webRequest ){
        ErrorDetails errorDetails = new ErrorDetails(alreadyExistException.getMessage(),webRequest.getDescription(true),new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
