package com.generation153.library.dto;

import com.generation153.library.entity.EnumLoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
    private Integer id;
    private LocalDate date;
    private LocalDate expReturnDate;
    private LocalDate returnDate;
    private EnumLoanStatus status;

    private Integer copyId;
    private String bookTitle;

    private Integer userId;
}
