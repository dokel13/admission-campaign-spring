package com.campaign.admission.exception;

public class UserValidatorRuntimeException extends RuntimeException {

    private String message;

    public UserValidatorRuntimeException() {
    }

    public UserValidatorRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
