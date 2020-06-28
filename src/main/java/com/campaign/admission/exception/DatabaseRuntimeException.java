package com.campaign.admission.exception;

public class DatabaseRuntimeException extends RuntimeException {

    private String message;

    public DatabaseRuntimeException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
