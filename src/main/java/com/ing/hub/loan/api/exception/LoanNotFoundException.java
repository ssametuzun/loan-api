package com.ing.hub.loan.api.exception;


public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }
}
