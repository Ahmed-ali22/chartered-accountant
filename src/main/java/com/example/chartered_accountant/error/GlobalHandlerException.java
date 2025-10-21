package com.example.chartered_accountant.error;

import com.example.chartered_accountant.error.exception.AppointmentException;
import com.example.chartered_accountant.error.exception.DomainException;
import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.error.model.ErrorResponse;
import com.example.chartered_accountant.util.time.TimingUtilities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder errorDescription = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField());
            errorDescription.append(error.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse(400 , errorMessage.toString() , errorDescription.toString() , TimingUtilities.currentTimestamp());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getErrorMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
       return ResponseEntity.status(HttpStatus.valueOf(ex.getErrorCode())).body(errorResponse);
    }

}
