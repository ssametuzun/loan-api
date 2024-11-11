package com.ing.hub.loan.api.model.request;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class CustomerCreateRequest {
    private String name;
    private String surname;
    private BigDecimal creditLimit;
}
