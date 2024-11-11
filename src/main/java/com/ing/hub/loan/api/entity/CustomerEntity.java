package com.ing.hub.loan.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private BigDecimal creditLimit = BigDecimal.valueOf(1000);

    private BigDecimal usedCreditLimit = BigDecimal.ZERO;
}
