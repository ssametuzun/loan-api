package com.ing.hub.loan.api.repository;


import com.ing.hub.loan.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
