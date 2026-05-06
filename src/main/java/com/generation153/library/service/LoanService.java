package com.generation153.library.service;

import com.generation153.library.dto.LoanCreateDTO;
import com.generation153.library.dto.LoanResponseDTO;
import com.generation153.library.dto.LoanUpdateDTO;
import com.generation153.library.entity.Loan;
import com.generation153.library.entity.User;

import java.util.List;

public interface LoanService {

    List<Loan> findAllLoans();

    Loan findLoanById(Integer id);

    Loan saveLoan(Loan loan);

    Loan replaceLoanById(Loan loan, Integer id);

    Loan updateLoanById(Loan loan, Integer id);

    void deleteLoanById(Integer id);

    List<Loan> findLateLoans();

    List<Loan> findAllActiveLoans();

    List<Loan> findAllLoansByUser(User user);

    Loan mapToEntity(LoanCreateDTO dto);

    Loan mapToEntity(LoanUpdateDTO dto);

    LoanResponseDTO mapToResponseDTO(Loan loan);

    List<LoanResponseDTO> mapToListResponseDTO(List<Loan> loans);
}
