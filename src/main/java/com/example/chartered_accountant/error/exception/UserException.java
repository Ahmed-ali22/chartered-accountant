package com.example.chartered_accountant.error.exception;

public class UserException extends DomainException {
    public UserException(int errorCode,String errorMessage,String description) {
        super(errorCode,errorMessage,description);
    }
}
