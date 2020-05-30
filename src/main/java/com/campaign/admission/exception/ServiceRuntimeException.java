package com.campaign.admission.exception;

public class ServiceRuntimeException extends RuntimeException {

    private String message;

    public ServiceRuntimeException() {
    }

    public ServiceRuntimeException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public ServiceRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
