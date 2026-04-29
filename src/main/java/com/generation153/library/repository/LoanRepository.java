package com.generation153.library.repository;

import com.generation153.library.entity.EnumLoanStatus;
import com.generation153.library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByReturnDateIsNullAndExpReturnDateBefore(LocalDate date);

    List<Loan> findByStatus(EnumLoanStatus status);

    List<Loan> findByUserId(Integer id);
}
