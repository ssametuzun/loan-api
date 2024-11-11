package com.ing.hub.loan.api.exception;


public class LoanAmountNotSufficientException extends RuntimeException {
    public LoanAmountNotSufficientException(String message) {
        super(message);
    }
}
