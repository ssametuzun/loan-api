package com.ing.hub.loan.api.model.request;


import lombok.Data;


@Data
public class PayLoadRequest {
    private Long loanId;
    private float amountToBePaid;
}
