package com.ing.hub.loan.api.exception;


public class UserNameAlreadyTakenException extends RuntimeException {
    public UserNameAlreadyTakenException(String message) {
        super(message);
    }
}
