package com.generation153.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorCreateDTO {
	
	@NotBlank(message = "Il nome non può essere nullo o vuoto")
	@Size(max = 100)
	private String firstName;
	@NotBlank(message = "Il cognome non può essere nullo o vuoto")
	@Size(max = 100)
	private String lastName;
	

}
