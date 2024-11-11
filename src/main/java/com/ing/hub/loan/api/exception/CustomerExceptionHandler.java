package com.ing.hub.loan.api.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;


@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Customer not found", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<?> loanNotFoundException(LoanNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Loan not found", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(NumberOfInstallmentsException.class)
    public ResponseEntity<?> numberOfInstallmentsException(NumberOfInstallmentsException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Number of Installment Exception", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(InterestRateException.class)
    public ResponseEntity<?> interestRateException(InterestRateException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Interest Rate Exception", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(NotFoundInstallmentException.class)
    public ResponseEntity<?> notFoundInstallmentException(NotFoundInstallmentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Not Found Installment", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(CreditPaidException.class)
    public ResponseEntity<?> creditPaidException(CreditPaidException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Credit completely paid. Cannot pay loan", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(LoanAmountNotSufficientException.class)
    public ResponseEntity<?> creditPaidException(LoanAmountNotSufficientException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Loan Amount Not Sufficient To Pay", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(UnknownError.class)
    public ResponseEntity<?> unknownError(UnknownError ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Unknown Error Occurred", Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<?> userExistError(UserAlreadyExist ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("User already exist", Collections.singletonList(ex.getMessage())));
    }
}
