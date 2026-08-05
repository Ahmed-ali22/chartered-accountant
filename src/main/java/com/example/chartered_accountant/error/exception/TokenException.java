package com.example.chartered_accountant.error.exception;

public class TokenException extends DomainException{
    public TokenException(int errorCode, String errorMessage, String description) {
        super(errorCode, errorMessage, description);
    }
}
