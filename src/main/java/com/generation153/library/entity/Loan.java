package com.generation153.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(name = "exp_return_date", nullable = false)
    private LocalDate expReturnDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumLoanStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;
}
