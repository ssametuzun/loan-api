package com.ing.hub.loan.api.mapper;


import com.ing.hub.loan.api.entity.CustomerEntity;
import com.ing.hub.loan.api.model.request.CustomerCreateRequest;
import com.ing.hub.loan.api.model.response.CreateCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerEntity fromCreateCustomerRequestToEntity(CustomerCreateRequest request);

    CreateCustomerResponse fromEntityToCreateCustomerResponse(CustomerEntity entity);

    List<CreateCustomerResponse> fromEntityToCreateCustomerResponse(List<CustomerEntity> entity);

}
