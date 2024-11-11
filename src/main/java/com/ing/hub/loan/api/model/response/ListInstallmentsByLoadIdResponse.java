package com.ing.hub.loan.api.model.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ListInstallmentsByLoadIdResponse {
    private Long loanId;
    private List<InstallmentResponse> installments;
}
