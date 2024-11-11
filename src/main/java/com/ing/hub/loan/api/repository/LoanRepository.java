package com.ing.hub.loan.api.repository;


import com.ing.hub.loan.api.entity.LoanEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Optional<List<LoanEntity>> findByCustomerId(Long customerId, Pageable pageable);
}
