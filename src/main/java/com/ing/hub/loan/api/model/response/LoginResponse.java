package com.ing.hub.loan.api.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String token;
    private final String type = "Bearer";
}
