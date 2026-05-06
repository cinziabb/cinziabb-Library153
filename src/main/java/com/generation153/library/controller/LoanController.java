package com.generation153.library.controller;

import com.generation153.library.dto.LoanCreateDTO;
import com.generation153.library.dto.LoanResponseDTO;
import com.generation153.library.dto.LoanUpdateDTO;
import com.generation153.library.entity.Loan;
import com.generation153.library.service.LoanServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoanController {

    private final LoanServiceImpl loanService;

    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/admin/loans")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<Loan> loans = loanService.findAllLoans();
        List<LoanResponseDTO> allResponseDTOs = loanService.mapToListResponseDTO(loans);
        return ResponseEntity.ok(allResponseDTOs);
    }

    @PostMapping("/admin/loans")
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanCreateDTO dto) {
        Loan loan = loanService.mapToEntity(dto);
        Loan savedLoan = loanService.saveLoan(loan);

        LoanResponseDTO loanResponseDTO = loanService.mapToResponseDTO(savedLoan);
        return new ResponseEntity<LoanResponseDTO>(loanResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/admin/loans/{id}")
    public ResponseEntity<LoanResponseDTO> updateLoanById(
            @RequestBody LoanUpdateDTO dto,
            @Min(value = 1, message = "L'ID deve essere almeno 1")
            @PathVariable Integer id) {
        Loan loan = loanService.mapToEntity(dto);
        Loan updatedLoan = loanService.updateLoanById(loan, id);
        LoanResponseDTO responseDTO = loanService.mapToResponseDTO(updatedLoan);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/admin/loans/{id}")
    public ResponseEntity<String> deleteLoanById(
            @Min(value = 1, message = "L'ID deve essere almeno 1")
            @PathVariable Integer id
    ) {
        loanService.deleteLoanById(id);
        return ResponseEntity.ok("Prestito eliminato con successo");
    }
}
