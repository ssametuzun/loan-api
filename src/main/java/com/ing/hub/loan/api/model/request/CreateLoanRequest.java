package com.ing.hub.loan.api.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanRequest {
    private Long customerId;
    private float amount;
    private float interestRate;
    private int numberOfInstallments;
}
