package com.ing.hub.loan.api.service;


import com.ing.hub.loan.api.entity.CustomerEntity;
import com.ing.hub.loan.api.mapper.CustomerMapper;
import com.ing.hub.loan.api.model.request.CustomerCreateRequest;
import com.ing.hub.loan.api.model.response.CreateCustomerResponse;
import com.ing.hub.loan.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CreateCustomerResponse createCustomer(CustomerCreateRequest request) {
        CustomerEntity createCustomerEntity = customerMapper.fromCreateCustomerRequestToEntity(request);
        CustomerEntity customerEntity = customerRepository.save(createCustomerEntity);
        log.info("CustomerEntity: {}", customerEntity);
        return customerMapper.fromEntityToCreateCustomerResponse(customerEntity);
    }

    public List<CreateCustomerResponse>  listAllCustomers() {
        List<CustomerEntity> allCustomers = customerRepository.findAll();
         return customerMapper.fromEntityToCreateCustomerResponse(allCustomers);
    }
}
