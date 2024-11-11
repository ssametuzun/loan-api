package com.ing.hub.loan.api.model.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ListLoansByCustomerIdResponse {
    private Long customerId;
    private List<LoanResponse> loanList;
}
