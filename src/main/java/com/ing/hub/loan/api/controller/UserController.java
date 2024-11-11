package com.ing.hub.loan.api.controller;


import com.ing.hub.loan.api.model.response.SignUpModel;
import com.ing.hub.loan.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/list-all-users")
    public ResponseEntity<List<SignUpModel>> listAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }
}
