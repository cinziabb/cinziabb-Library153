package com.generation153.library.service;

import com.generation153.library.entity.Copy;
import com.generation153.library.entity.Loan;
import com.generation153.library.entity.User;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.CopyRepository;
import com.generation153.library.repository.LoanRepository;
import com.generation153.library.repository.UserRepository;

import java.util.List;

public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, CopyRepository copyRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findLoanById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        return loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Nessun prestito con id: " + id));
    }

    @Override
    public Loan saveLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        User user = findUserInsideLoan(loan);
        loan.setUser(user);

        Copy copy = findCopyInsideLoan(loan);
        loan.setCopy(copy);

        return loanRepository.save(loan);
    }

    @Override
    public Loan updateLoanById(Loan loan, Integer id) {
        if (loan == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        Loan savedLoan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestito non trovato con id: " + id));

        if (loan.getDate() != null && (savedLoan.getReturnDate() == null || !loan.getDate().isAfter(savedLoan.getReturnDate()))) {
            savedLoan.setDate(loan.getDate());
            // se cambia la data di inizio devo cambiare anche la data entro il cui il libro deve essere riconsegnato (7 giorni per un prestito)
            savedLoan.setDate(loan.getDate().plusDays(7));
        }

        return null;
    }

    @Override
    public void deleteLoanById(Integer id) {

    }

    private User findUserInsideLoan(Loan loan) {
        return userRepository.findById(loan.getUser().getId()).orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + loan.getUser().getId()));
    }

    private Copy findCopyInsideLoan(Loan loan) {
        return copyRepository.findById(loan.getCopy().getId()).orElseThrow(() -> new NotFoundException("Copia non trovata con id: " + loan.getCopy().getId()));
    }
}
