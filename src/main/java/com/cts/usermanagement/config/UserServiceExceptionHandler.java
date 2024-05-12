package com.cts.usermanagement.config;

import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.external.ErrorResponse;
import com.cts.usermanagement.external.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleRunTimeException(UserNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                errors(Collections.singletonList(Error.builder()
                        .code(exception.getErrorCode())
                        .message(exception.getMessage())
                        .build()))
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                errors(Collections.singletonList(Error.builder()
                        .code(exception.getErrorCode())
                        .message(exception.getMessage())
                        .build()))
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAnyValidationError(MethodArgumentNotValidException exception) {
        List<Error> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new Error("BadArgument", x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errors)
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }*/
}
