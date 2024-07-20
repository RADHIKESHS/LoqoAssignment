package com.loqoAi.ProductManagement.Exceptions;

import java.util.Date;

import lombok.Data;

@Data
class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // getters and setters
}
