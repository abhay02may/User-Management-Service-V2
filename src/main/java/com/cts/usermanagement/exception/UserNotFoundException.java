package com.cts.usermanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNotFoundException extends Throwable {
    String errorCode;
    int status;
    public UserNotFoundException(String message,String errorCode,int status) {
        super(message);
        this.errorCode=errorCode;
        this.status=status;
    }
}
