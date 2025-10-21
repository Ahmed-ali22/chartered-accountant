package com.example.chartered_accountant.error.exception;

public class AppointmentException extends DomainException {
    public AppointmentException(int errorCode,String errorMessage,String description) {
        super(errorCode,errorMessage,description);
    }
}
