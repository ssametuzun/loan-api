package com.ing.hub.loan.api.service;


import com.ing.hub.loan.api.entity.CustomerEntity;
import com.ing.hub.loan.api.entity.InstallmentEntity;
import com.ing.hub.loan.api.entity.LoanEntity;
import com.ing.hub.loan.api.exception.UnknownError;
import com.ing.hub.loan.api.exception.*;
import com.ing.hub.loan.api.mapper.InstallmentMapper;
import com.ing.hub.loan.api.mapper.LoanMapper;
import com.ing.hub.loan.api.model.request.CreateLoanRequest;
import com.ing.hub.loan.api.model.request.PayLoadRequest;
import com.ing.hub.loan.api.model.response.InstallmentResponse;
import com.ing.hub.loan.api.model.response.LoanResponse;
import com.ing.hub.loan.api.model.response.PayloadResponse;
import com.ing.hub.loan.api.repository.CustomerRepository;
import com.ing.hub.loan.api.repository.InstallmentRepository;
import com.ing.hub.loan.api.repository.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class LoanApiService {

    @Value("${domain.allowing-number-of-installment}")
    private final List<Integer> allowingNumberOfInstallment;
    @Value("${domain.allowing-interest-rate}")
    private final List<Float> allowingInterestRate;

    private InstallmentRepository installmentRepository;
    private CustomerRepository customerRepository;
    private LoanRepository loanRepository;

    private InstallmentMapper installmentMapper;
    private LoanMapper loanMapper;

    public LoanResponse createLoan(CreateLoanRequest request) {
        if (!allowingNumberOfInstallment.contains(request.getNumberOfInstallments())) {
            throw new NumberOfInstallmentsException("Number of interest not allowed. It can be " + allowingNumberOfInstallment);
        }

        if (!allowingInterestRate.contains(request.getInterestRate())) {
            throw new InterestRateException("Interest Rate not allowed. It can be " + allowingInterestRate);
        }

        CustomerEntity customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        BigDecimal remainingCreditLimit = customer.getCreditLimit().subtract(BigDecimal.valueOf(request.getAmount()));
        if (remainingCreditLimit.signum() == -1) {
            throw new LoanAmountNotSufficientException("Not sufficient remaining loan amount");
        }

        BigDecimal loanAmount = BigDecimal.valueOf(request.getAmount() * (1 + request.getInterestRate())).divide(BigDecimal.ONE, 4, RoundingMode.FLOOR);
        LoanEntity createdLoan = createNewLoan(customer, loanAmount, request.getNumberOfInstallments());
        createInstallment(createdLoan, request.getNumberOfInstallments());
        updateCustomer(customer, createdLoan);
        return loanMapper.toResponse(createdLoan);
    }

    public List<LoanResponse> listLoanByCustomerId(Long customerId, Pageable pageable) {
        if (customerId == null) {
            throw new CustomerNotFoundException("Not found customer by id: ");
        }

        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (Objects.equals(customer.getId(), customerId)) {
            List<LoanEntity> customerLoans = loanRepository.findByCustomerId(customer.getId(), pageable)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: " + customerId));

            return loanMapper.toResponseList(customerLoans);
        }
        throw new UnknownError("Mismatched customer id in the parameter with logged id  " + customerId);
    }

    public List<InstallmentResponse> listInstallmentsByLoadId(Long loanId, Pageable pageable) {
        List<InstallmentEntity> installmentsByLoanId = installmentRepository.findAllByLoanId(loanId, pageable);
        return installmentMapper.toReponseList(installmentsByLoanId);
    }

    public PayloadResponse payLoan(PayLoadRequest request) {
        LoanEntity loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException("Loan not found by id: " + request.getLoanId()));

        if (loan.isPaid()) {
            throw new CreditPaidException();
        }

        List<InstallmentEntity> installmentEntities = installmentRepository.findAllByLoanId(request.getLoanId()).stream().filter(item -> !item.isPaid())
                .sorted(Comparator.comparing(InstallmentEntity::getDueDate)).collect(Collectors.toList());

        if (installmentEntities.isEmpty()) {
            throw new NotFoundInstallmentException("Active Installment not found by loan id: " + request.getLoanId());
        }

        BigDecimal amountToBePaid = BigDecimal.valueOf(request.getAmountToBePaid());
        int numberOfPayment = getNumberOfPayment(installmentEntities, amountToBePaid);

        return makePayment(loan, installmentEntities.subList(0, numberOfPayment), numberOfPayment == installmentEntities.size());
    }

    private static int getNumberOfPayment(List<InstallmentEntity> installmentEntities, BigDecimal amountToBePaid) {
        BigDecimal installmentAmount = installmentEntities.get(0).getAmount();

        if (amountToBePaid.compareTo(installmentAmount) < 0) {
            throw new LoanAmountNotSufficientException("Loan amount not sufficient. Each pay should be " + installmentAmount);
        }

        int numberOfPayment = amountToBePaid.divide(installmentAmount, 0, RoundingMode.FLOOR).intValue();

        // If much more money
        if (numberOfPayment > installmentEntities.size()) {
            numberOfPayment = installmentEntities.size();
        }
        return numberOfPayment;
    }

    private LoanEntity createNewLoan(CustomerEntity customer, BigDecimal loanAmount, int numberOfInstallments) {
        LoanEntity newLoan = new LoanEntity();
        newLoan.setCustomer(customer);
        newLoan.setLoanAmount(loanAmount);
        newLoan.setNumberOfInstallments(numberOfInstallments);
        LoanEntity loan = loanRepository.save(newLoan);
        log.info("Created new loan: {}", loan);
        return loan;
    }

    private void updateCustomer(CustomerEntity customer, LoanEntity loan) {
        BigDecimal remainingCreditLimit = customer.getCreditLimit().subtract(loan.getLoanAmount());
        BigDecimal usedCreditLimit = customer.getUsedCreditLimit().add(loan.getLoanAmount());

        customer.setCreditLimit(remainingCreditLimit);
        customer.setUsedCreditLimit(usedCreditLimit);
        CustomerEntity entity = customerRepository.save(customer);
        log.info("Customer updated: {}", entity);
    }

    private void createInstallment(LoanEntity loan, int numberOfInstallment) {
        List<InstallmentEntity> installments = new ArrayList<>();
        BigDecimal installmentAmount = loan.getLoanAmount().divide(BigDecimal.valueOf(numberOfInstallment), 4, RoundingMode.FLOOR);
        for (int i = 0; i < numberOfInstallment; i++) {
            LocalDate dueDate = LocalDate.now().plusMonths(i + 1).withDayOfMonth(1);
            InstallmentEntity installment = new InstallmentEntity();
            installment.setLoan(loan);
            installment.setAmount(installmentAmount);
            installment.setDueDate(dueDate);
            installments.add(installment);
        }
        List<InstallmentEntity> installmentEntities = installmentRepository.saveAll(installments);
        log.info("Installments created: {}", installmentEntities);
    }

    private PayloadResponse makePayment(LoanEntity loan, List<InstallmentEntity> installmentToBePaid, boolean isCreditCompleted) {
        BigDecimal totalAmountPaid = BigDecimal.ZERO;
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;
        BigDecimal totalPenaltyAmount = BigDecimal.ZERO;
        for (InstallmentEntity currentInstallment : installmentToBePaid) {
            BigDecimal installmentAmount = currentInstallment.getAmount();

            if (currentInstallment.getDueDate().isAfter(LocalDate.now())) { // discount
                long daysBetween = LocalDate.now().until(currentInstallment.getDueDate()).getDays();
                BigDecimal discountAmount = BigDecimal.valueOf(0.001).multiply(BigDecimal.valueOf(daysBetween));
                installmentAmount = installmentAmount.subtract(discountAmount);
                totalDiscountAmount = totalDiscountAmount.add(discountAmount);
            } else if (currentInstallment.getDueDate().isBefore(LocalDate.now())) { // penalty
                long daysBetween = currentInstallment.getDueDate().until(LocalDate.now()).getDays();
                BigDecimal penaltyAmount = BigDecimal.valueOf(0.001).multiply(BigDecimal.valueOf(daysBetween));
                installmentAmount = installmentAmount.add(penaltyAmount);
                totalPenaltyAmount = totalPenaltyAmount.add(penaltyAmount);
            }

            currentInstallment.setPaid(true);
            currentInstallment.setPaymentDate(LocalDateTime.now());
            currentInstallment.setPaidAmount(installmentAmount);
            totalAmountPaid = totalAmountPaid.add(installmentAmount);
        }

        loan.setPaid(isCreditCompleted);

        List<InstallmentEntity> installmentEntities = installmentRepository.saveAll(installmentToBePaid);
        log.info("Installment made: {}", installmentEntities);
        LoanEntity loanEntity = loanRepository.save(loan);
        log.info("Loan Updated: {}", loanEntity);

        PayloadResponse response = new PayloadResponse();
        response.setTotalAmountSpent(totalAmountPaid);
        response.setTotalDiscountAmount(totalDiscountAmount);
        response.setTotalPenaltyAmount(totalPenaltyAmount);
        response.setTotalInstallmentPaid(installmentToBePaid.size());
        return response;
    }

}
