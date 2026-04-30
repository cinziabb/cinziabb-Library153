package com.generation153.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PublisherCreateDTO {
	
	@NotNull
	private Integer id;
	@NotBlank(message = "Stringa cognome nulla o vuota!")
	private String name;
}
