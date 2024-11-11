package com.ing.hub.loan.api.exception;


public class CreditPaidException extends RuntimeException {

    public CreditPaidException() {
        super();
    }

    public CreditPaidException(String message) {
        super(message);
    }
}
