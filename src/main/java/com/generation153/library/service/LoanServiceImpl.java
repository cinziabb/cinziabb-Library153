package com.generation153.library.service;

import com.generation153.library.entity.*;
import com.generation153.library.exception.NotAvailableException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.exception.NotLendableException;
import com.generation153.library.repository.BookRepository;
import com.generation153.library.repository.CopyRepository;
import com.generation153.library.repository.LoanRepository;
import com.generation153.library.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, CopyRepository copyRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
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

        // cerco la copia
        Copy copy = findCopyInsideLoan(loan);
        loan.setCopy(copy);

        // controllo che il libro associato alla copia sia prestabile
        if (!isBookInsideCopyLendable(copy)) {
            throw new NotLendableException("Libro non prestabile");
        }

        // controllo che la copia sia disponibile
        if (!copy.isAvailable()) {
            throw new NotAvailableException("Copia non disponibile");
        }

        User user = findUserInsideLoan(loan);
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    @Override
    public Loan replaceLoanById(Loan loan, Integer id) {
        if (loan == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        Loan replacedLoan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestito non trovato"));

        replacedLoan.setDate(loan.getDate());
        replacedLoan.setExpReturnDate(loan.getExpReturnDate());
        replacedLoan.setReturnDate(loan.getReturnDate());
        replacedLoan.setStatus(loan.getStatus());

        Copy copy = findCopyInsideLoan(loan);
        replacedLoan.setCopy(copy);

        User user = findUserInsideLoan(loan);
        replacedLoan.setUser(user);

        return loanRepository.save(replacedLoan);
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
            // se cambia la data di inizio, cambia anche la data di restituzione prevista (7 giorni per un prestito)
            savedLoan.setExpReturnDate(loan.getDate().plusDays(7));
        }

        if (loan.getReturnDate() != null && loan.getReturnDate().isBefore(savedLoan.getDate())) {
            throw new IllegalArgumentException("La data di ritorno deve essere dopo quella di inizio prestito");
        }

        if (loan.getReturnDate() != null && loan.getReturnDate().isAfter(savedLoan.getDate())) {
            savedLoan.setReturnDate(loan.getReturnDate());

            // aggiorno lo stato del prestito
            if (savedLoan.getExpReturnDate() != null && loan.getReturnDate().isAfter(savedLoan.getExpReturnDate())) {
                savedLoan.setStatus(EnumLoanStatus.LATE);
            } else {
                savedLoan.setStatus(EnumLoanStatus.RETURNED);
            }

            // rendo la copia disponibile dopo il ritorno
            Copy copy = findCopyInsideLoan(savedLoan);
            copy.setAvailable(true);
        }

        if (loan.getUser() != null) {
            User user = findUserInsideLoan(loan);
            savedLoan.setUser(user);
        }

        if (loan.getCopy() != null) {
            Copy copy = findCopyInsideLoan(loan);
            savedLoan.setCopy(copy);
        }

        return loanRepository.save(savedLoan);
    }

    @Override
    public void deleteLoanById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        Loan loan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestito non trovato con id: " + id));

        loanRepository.delete(loan);
    }

    @Override
    public List<Loan> findLateLoans() {
        return loanRepository.findByReturnDateIsNullAndExpReturnDateBefore(LocalDate.now());
    }

    @Override
    public List<Loan> findAllActiveLoans() {
        return loanRepository.findByStatus(EnumLoanStatus.ACTIVE);
    }

    @Override
    public List<Loan> findAllLoansByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Utente nullo");
        }

        return loanRepository.findByUserId(user.getId());
    }

    private User findUserInsideLoan(Loan loan) {
        return userRepository.findById(loan.getUser().getId()).orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + loan.getUser().getId()));
    }

    private Copy findCopyInsideLoan(Loan loan) {
        return copyRepository.findById(loan.getCopy().getId()).orElseThrow(() -> new NotFoundException("Copia non trovata con id: " + loan.getCopy().getId()));
    }

    private boolean isBookInsideCopyLendable(Copy copy) {
        Book book = bookRepository.findById(copy.getBook().getId()).orElseThrow(() -> new NotFoundException("Libro non trovato con id: " + copy.getBook().getId()));
        return book.getLendable();
    }
}
