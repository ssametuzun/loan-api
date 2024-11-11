package com.ing.hub.loan.api.repository;


import com.ing.hub.loan.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByCustomerId(Long customerId);
}