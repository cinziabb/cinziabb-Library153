package com.generation153.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCreateDTO {
	
	@NotBlank(message = "Stringa nome nulla o vuota!")
	@Size(max = 100)
	private String firstName;
	@NotBlank(message = "Stringa cognome nulla o vuota!")
	@Size(max = 100)
	private String lastName;

}
