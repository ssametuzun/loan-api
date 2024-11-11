package com.ing.hub.loan.api.model.response;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateLoanResponse {
    private LoanResponse loan;
}
