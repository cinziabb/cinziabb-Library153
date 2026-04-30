package com.generation153.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDTO {
	
	@NotNull
	private Integer id;
	@NotBlank(message = "Nome categoria nullo o vuoto!")
	@Size(max = 100)
	private String name;
	

}
