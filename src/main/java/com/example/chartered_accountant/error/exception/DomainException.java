package com.example.chartered_accountant.error.exception;

import ch.qos.logback.core.util.TimeUtil;
import com.example.chartered_accountant.util.time.TimingUtilities;
import lombok.Getter;
import lombok.Value;

import java.sql.Timestamp;

@Getter
public abstract class DomainException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final String description;
    private final Timestamp timestamp;

    protected DomainException(int errorCode, String errorMessage, String description) {
        super(description);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
        this.timestamp = TimingUtilities.currentTimestamp();
    }
}
