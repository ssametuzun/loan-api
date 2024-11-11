package com.ing.hub.loan.api.model.response;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class InstallmentResponse {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    private boolean isPaid;
}
