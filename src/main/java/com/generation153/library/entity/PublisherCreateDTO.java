package com.generation153.library.entity;

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
public class PublisherCreateDTO {
	
	@NotBlank(message = "Nome editore nullo o vuoto")
	@Size(max = 100)
	private String name;

}
