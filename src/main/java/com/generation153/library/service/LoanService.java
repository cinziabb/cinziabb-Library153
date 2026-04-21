package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Loan;

public interface LoanService {

	List<Loan> findAllLoans();
	Loan findLoanById(Integer id);
	Loan saveLoan(Loan loan);
	Loan updateLoanById(Loan loan, Integer id);
	void deleteLoanById(Integer id);
}
