package com.ing.hub.loan.api.model.response;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class PayloadResponse {
    private BigDecimal totalAmountSpent;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalPenaltyAmount;
    private int totalInstallmentPaid;

}
