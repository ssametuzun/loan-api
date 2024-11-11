package com.ing.hub.loan.api.controller;


import com.ing.hub.loan.api.model.request.CreateLoanRequest;
import com.ing.hub.loan.api.model.request.PayLoadRequest;
import com.ing.hub.loan.api.model.response.*;
import com.ing.hub.loan.api.service.LoanApiService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/loan")
@AllArgsConstructor
public class LoanApiController {

    private LoanApiService loanApiService;

    @PostMapping("/create-loan")
    public ResponseEntity<CreateLoanResponse> createLoan(@RequestBody CreateLoanRequest request) {
        LoanResponse loan = loanApiService.createLoan(request);
        return ResponseEntity.ok(CreateLoanResponse.builder().loan(loan).build());
    }

    @GetMapping("/list-loan-by-customer-id")
    public ResponseEntity<ListLoansByCustomerIdResponse> listLoansByCustomerId(@RequestParam final Long customerId,
                                                                               @PageableDefault() final Pageable pageable) {
        List<LoanResponse> loanResponses = loanApiService.listLoanByCustomerId(customerId, pageable);
        return ResponseEntity.ok(ListLoansByCustomerIdResponse.builder().customerId(customerId).loanList(loanResponses).build());
    }

    @GetMapping("/list-installments-by-loan-id")
    public ResponseEntity<ListInstallmentsByLoadIdResponse> listInstallmentsByLoadId(@RequestParam final Long loanId,
                                                                                     @PageableDefault() final Pageable pageable) {
        List<InstallmentResponse> installmentResponseList = loanApiService.listInstallmentsByLoadId(loanId, pageable);
        return ResponseEntity.ok(ListInstallmentsByLoadIdResponse.builder().installments(installmentResponseList).loanId(loanId).build());
    }

    @PostMapping("/pay-loan")
    public ResponseEntity<PayloadResponse> payLoan(@RequestBody PayLoadRequest request) {
        PayloadResponse response = loanApiService.payLoan(request);
        return ResponseEntity.ok(response);
    }

}
