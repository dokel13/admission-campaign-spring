package com.campaign.admission.exception;

public class AdmissionValidatorRuntimeException extends RuntimeException {

    private String message;

    public AdmissionValidatorRuntimeException() {
    }

    public AdmissionValidatorRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
