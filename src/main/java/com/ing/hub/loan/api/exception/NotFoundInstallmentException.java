package com.ing.hub.loan.api.exception;


public class NotFoundInstallmentException extends RuntimeException {
    public NotFoundInstallmentException(String message) {
        super(message);
    }
}
