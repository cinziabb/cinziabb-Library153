package com.generation153.library.dto;

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
public class LoanUpdateDTO {

    private LocalDate date;
    private LocalDate returnDate;

    private Integer userId;
    private Integer copyId;
}
