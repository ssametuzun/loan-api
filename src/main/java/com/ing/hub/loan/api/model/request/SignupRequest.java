package com.ing.hub.loan.api.model.request;


import lombok.Data;


@Data
public class SignupRequest {
    private Long customerId;
    private String username;
    private String password;
    private boolean admin;
}