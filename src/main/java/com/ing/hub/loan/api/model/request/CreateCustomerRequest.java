package com.ing.hub.loan.api.model.request;


import lombok.Data;


@Data
public class CreateCustomerRequest {
    private String identityNumber;
    private String name;
    private String phone;
    private String email;
}
