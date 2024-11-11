package com.ing.hub.loan.api.model.response;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateCustomerResponse {
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
}
