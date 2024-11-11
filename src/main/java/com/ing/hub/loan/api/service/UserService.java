package com.ing.hub.loan.api.service;


import com.ing.hub.loan.api.mapper.UserMapper;
import com.ing.hub.loan.api.model.response.SignUpModel;
import com.ing.hub.loan.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<SignUpModel> listAllUsers() {
        return userMapper.toSignUpModel(userRepository.findAll());
    }

}
