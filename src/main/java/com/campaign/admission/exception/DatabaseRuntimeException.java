package com.campaign.admission.exception;

public class DatabaseRuntimeException extends RuntimeException {

    private String message;

    public DatabaseRuntimeException() {
    }

    public DatabaseRuntimeException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public DatabaseRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
