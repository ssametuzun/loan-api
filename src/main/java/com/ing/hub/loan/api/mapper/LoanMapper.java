package com.ing.hub.loan.api.mapper;


import com.ing.hub.loan.api.entity.CustomerEntity;
import com.ing.hub.loan.api.entity.LoanEntity;
import com.ing.hub.loan.api.model.response.LoanResponse;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    @Mappings(
            {
                    @Mapping(source = "customer", target = "customerId", qualifiedByName = "convertCustomerEntityToCustomerId")
            }
    )
    LoanResponse toResponse(LoanEntity loanEntity);

    @Mapping(source = "customer", target = "customerId", qualifiedByName = "convertCustomerEntityToCustomerId")
    List<LoanResponse> toResponseList(List<LoanEntity> loanEntityList);

    @Named("convertCustomerEntityToCustomerId")
    default Long convertCustomerEntityToCustomerId(final CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        return customerEntity.getId();
    }

}
