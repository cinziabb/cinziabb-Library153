package com.generation153.library.service;

import com.generation153.library.dto.LoanCreateDTO;
import com.generation153.library.dto.LoanResponseDTO;
import com.generation153.library.dto.LoanUpdateDTO;
import com.generation153.library.entity.*;
import com.generation153.library.exception.*;
import com.generation153.library.repository.BookRepository;
import com.generation153.library.repository.CopyRepository;
import com.generation153.library.repository.LoanRepository;
import com.generation153.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private static final int LOAN_DURATION_DAYS = 7;
    private static final int MAX_LOAN_PER_USER = 3;

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

    @Transactional
    @Override
    public Loan saveLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        // cerco la copia
        Copy copy = findCopyInsideLoan(loan);

        // controllo che il libro associato alla copia sia prestabile
        Book book = findBookInsideCopy(copy);
        if (!book.getLendable()) {
            throw new NotLendableException("Libro non prestabile");
        }

        // controllo che la copia sia disponibile
        if (!copy.getAvailable()) {
            throw new NotAvailableException("Copia non disponibile");
        }

        User user = findUserInsideLoan(loan);

        // verifico se l'utente è bloccato
        if (user.isBlocked()) {
            throw new UserBlockedException("Utente bloccato");
        }

        // conto i prestiti attivi dell'utente
        int numPrestiti = loanRepository.findByStatusAndUserId(EnumLoanStatus.ACTIVE, user.getId()).size();

        if (numPrestiti == MAX_LOAN_PER_USER) {
            throw new MaxLoansReachedException("L'utente ha raggiunto il numero di prestiti massimo");
        }

        copy.setAvailable(false);
        loan.setCopy(copy);
        loan.setUser(user);

        copyRepository.save(copy);

        // imposto il prestito su active
        loan.setStatus(EnumLoanStatus.ACTIVE);

        // imposto la data di avvenuto prestito
        loan.setDate(LocalDate.now());

        // calcolo la data di ritorno prevista
        loan.setExpReturnDate(loan.getDate().plusDays(LOAN_DURATION_DAYS));

        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public Loan replaceLoanById(Loan loan, Integer id) {
        if (loan == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        Loan replacedLoan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestito non trovato con id" + id));

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

    @Transactional
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
            savedLoan.setExpReturnDate(loan.getDate().plusDays(LOAN_DURATION_DAYS));
        }

        if (loan.getReturnDate() != null) {
            if (loan.getReturnDate().isBefore(savedLoan.getDate())) {
                throw new IllegalArgumentException("La data di ritorno deve essere dopo quella di inizio prestito");
            }

            savedLoan.setReturnDate(loan.getReturnDate());

            // determino se il reso è in ritardo
            if (savedLoan.getExpReturnDate() != null && loan.getReturnDate().isAfter(savedLoan.getExpReturnDate())) {
                savedLoan.setStatus(EnumLoanStatus.LATE);
            } else {
                savedLoan.setStatus(EnumLoanStatus.RETURNED);
            }

            // rendo la copia disponibile
            Copy copy = savedLoan.getCopy();
            copy.setAvailable(true);
            copyRepository.save(copy);
        }

        if (loan.getUser() != null) {
            User user = findUserInsideLoan(loan);
            savedLoan.setUser(user);
        }

        if (loan.getCopy() != null && savedLoan.getReturnDate() == null) {
            Copy copy = findCopyInsideLoan(loan);
            savedLoan.setCopy(copy);
        }

        return loanRepository.save(savedLoan);
    }

    @Override
    @Transactional
    public void deleteLoanById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id nullo");
        }

        Loan loan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestito non trovato con id: " + id));

        // se elimino il prestito la copia torna disponibile
        Copy copy = findCopyInsideLoan(loan);
        copy.setAvailable(true);
        copyRepository.save(copy);

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

    @Override
    public Loan mapToEntity(LoanCreateDTO dto) {
        Loan loan = new Loan();

        loan.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + dto.getUserId())));

        loan.setCopy(copyRepository.findById(dto.getCopyId()).orElseThrow(() -> new NotFoundException("Copia non trovata con id: " + dto.getCopyId())));

        return loan;
    }

    @Override
    public Loan mapToEntity(LoanUpdateDTO dto) {
        Loan loan = new Loan();

        loan.setDate(dto.getDate());
        loan.setReturnDate(dto.getReturnDate());
        if (dto.getCopyId() != null) {
            loan.setCopy(copyRepository.findById(dto.getCopyId()).orElseThrow(() -> new NotFoundException("Copia non trovata con id: " + dto.getCopyId())));
        }

        if (dto.getUserId() != null) {
            loan.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + dto.getUserId())));
        }

        return loan;
    }

    @Override
    public LoanResponseDTO mapToResponseDTO(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();

        dto.setId(loan.getId());
        dto.setDate(loan.getDate());
        dto.setExpReturnDate(loan.getExpReturnDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());
        dto.setCopyId(loan.getCopy().getId());
        dto.setBookTitle(loan.getCopy().getBook().getTitle());
        dto.setUserId(loan.getUser().getId());
        return dto;
    }

    @Override
    public List<LoanResponseDTO> mapToListResponseDTO(List<Loan> loans) {
        return loans.stream().map(this::mapToResponseDTO).toList();
    }

    private User findUserInsideLoan(Loan loan) {
        return userRepository.findById(loan.getUser().getId()).orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + loan.getUser().getId()));
    }

    private Copy findCopyInsideLoan(Loan loan) {
        return copyRepository.findById(loan.getCopy().getId()).orElseThrow(() -> new NotFoundException("Copia non trovata con id: " + loan.getCopy().getId()));
    }

    private Book findBookInsideCopy(Copy copy) {
        return bookRepository.findById(copy.getBook().getId()).orElseThrow(() -> new NotFoundException("Libro non trovato con id: " + copy.getBook().getId()));
    }
}
