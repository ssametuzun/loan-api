package com.ing.hub.loan.api.repository;


import com.ing.hub.loan.api.entity.InstallmentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InstallmentRepository extends JpaRepository<InstallmentEntity, Long> {

    List<InstallmentEntity> findAllByLoanId(Long loanId, Pageable pageable);

    List<InstallmentEntity> findAllByLoanId(Long loanId);
}
