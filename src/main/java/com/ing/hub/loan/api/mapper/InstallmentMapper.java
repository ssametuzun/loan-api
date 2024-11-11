package com.ing.hub.loan.api.mapper;


import com.ing.hub.loan.api.entity.InstallmentEntity;
import com.ing.hub.loan.api.model.response.InstallmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstallmentMapper {

    List<InstallmentResponse> toReponseList(List<InstallmentEntity> installmentEntityList);
}
