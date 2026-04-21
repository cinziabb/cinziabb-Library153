package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Loan;


public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
