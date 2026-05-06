package com.generation153.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreateDTO {

    @NotNull(message = "userId nullo")
    private Integer userId;

    @NotNull(message = "copyId nullo")
    private Integer copyId;
}
