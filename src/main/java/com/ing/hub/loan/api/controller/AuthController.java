package com.ing.hub.loan.api.controller;


import com.ing.hub.loan.api.exception.ErrorResponse;
import com.ing.hub.loan.api.exception.UserNameAlreadyTakenException;
import com.ing.hub.loan.api.model.request.LoginRequest;
import com.ing.hub.loan.api.model.request.SignupRequest;
import com.ing.hub.loan.api.model.response.LoginResponse;
import com.ing.hub.loan.api.model.response.SignUpModel;
import com.ing.hub.loan.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) throws BadCredentialsException {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(new LoginResponse(request.getUsername(), token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), null));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest request) throws UserNameAlreadyTakenException {
        try {
            SignUpModel response = authService.signUp(request);
            return ResponseEntity.ok(response);
        } catch (UserNameAlreadyTakenException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), null));
        }
    }
}