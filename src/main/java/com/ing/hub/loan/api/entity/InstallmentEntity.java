package com.ing.hub.loan.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "installment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private LocalDate dueDate;

    private LocalDateTime paymentDate;

    private boolean isPaid;
}
