package com.cts.usermanagement.config;

import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.external.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceCustomException(UserNotFoundException customException){
        ErrorResponse errorResponse=ErrorResponse.builder().
                errorCode(customException.getErrorCode()).
                errorMessage(customException.getMessage()).
                build();
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(customException.getStatus()));
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleMethodArgumentNotValidException(MethodArgumentNotValidException customException){
       Map<String,String> map=new HashMap<>();
       customException.getBindingResult().getFieldErrors().
               forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
       return map;
    }*/
}
