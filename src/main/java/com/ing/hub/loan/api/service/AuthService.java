package com.ing.hub.loan.api.service;


import com.ing.hub.loan.api.entity.CustomerEntity;
import com.ing.hub.loan.api.entity.UserEntity;
import com.ing.hub.loan.api.exception.CustomerNotFoundException;
import com.ing.hub.loan.api.exception.UserAlreadyExist;
import com.ing.hub.loan.api.exception.UserNameAlreadyTakenException;
import com.ing.hub.loan.api.mapper.UserMapper;
import com.ing.hub.loan.api.model.request.LoginRequest;
import com.ing.hub.loan.api.model.request.SignupRequest;
import com.ing.hub.loan.api.model.response.SignUpModel;
import com.ing.hub.loan.api.repository.CustomerRepository;
import com.ing.hub.loan.api.repository.UserRepository;
import com.ing.hub.loan.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final CustomerRepository customerRepository;

    public SignUpModel signUp(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserNameAlreadyTakenException("Username already taken!");
        }

        UserEntity user = request.isAdmin() ? createAdmin(request) : createUser(request);
        UserEntity userEntity = userRepository.save(user);
        log.info("User signed up: {}", userEntity);

        return userMapper.toSignUpModel(userEntity);
    }

    public String login(LoginRequest loginRequest) throws BadCredentialsException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());
        log.info("login token: {}", jwt);
        return jwt;
    }

    private UserEntity createAdmin(SignupRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        return user;
    }

    private UserEntity createUser(SignupRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id" + request.getCustomerId()));

        Optional<UserEntity> existingCustomer = userRepository.findByCustomerId(customer.getId());
        if (existingCustomer.isPresent()) {
            throw new UserAlreadyExist("User already created by customer id: " + customer.getId());
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setCustomer(customer);
        return user;
    }
}
