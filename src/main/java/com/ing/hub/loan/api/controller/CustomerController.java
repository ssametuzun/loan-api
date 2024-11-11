package com.ing.hub.loan.api.controller;


import com.ing.hub.loan.api.model.request.CustomerCreateRequest;
import com.ing.hub.loan.api.model.response.CreateCustomerResponse;
import com.ing.hub.loan.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody final CustomerCreateRequest request) {
        CreateCustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-all-customers")
    public ResponseEntity<?> listAllCustomers() {
        List<CreateCustomerResponse> response = customerService.listAllCustomers();
        return ResponseEntity.ok(response);
    }
}
