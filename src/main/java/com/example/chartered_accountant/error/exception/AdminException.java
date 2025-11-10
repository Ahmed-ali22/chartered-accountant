package com.example.chartered_accountant.error.exception;

import lombok.Getter;

public class AdminException extends DomainException{
    public AdminException(int errorCode,String errorMessage,String description) {
        super(errorCode,errorMessage,description);
    }
}
