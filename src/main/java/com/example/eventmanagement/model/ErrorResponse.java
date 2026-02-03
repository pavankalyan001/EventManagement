package com.example.eventmanagement.model;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(Instant timestamp, int status, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
