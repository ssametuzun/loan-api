package com.ing.hub.loan.api.model.response;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class LoanResponse {
    private BigDecimal loanAmount;
    private String customerId;
    private int numberOfInstallments;
    private boolean isPaid;
}
