package com.ing.hub.loan.api.mapper;


import com.ing.hub.loan.api.entity.UserEntity;
import com.ing.hub.loan.api.model.response.SignUpModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    SignUpModel toSignUpModel(UserEntity userEntity);

    List<SignUpModel> toSignUpModel(List<UserEntity> userEntity);

}
