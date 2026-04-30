package com.generation153.library.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class BookCreateDTO {

	@NotBlank(message = "isbn nullo o vuoto")
	private String isbn;
	@NotBlank(message = "title nullo o vuoto")
	private String title;
	@NotBlank(message = "language nullo o vuoto")
	private String language;
	@NotBlank(message = "imageUri nullo o vuoto")
	private String imageUri;
	@NotNull(message = "edition nullo")
	private Integer edition;
	@NotNull(message = "lendable nullo")
	private Boolean lendable;
	
	@NotNull(message = "publisherId nullo")
	private Integer publisherId;
	@NotNull(message = "categoryId nullo")
	private Integer categoryId;
	@NotEmpty(message = "authorsId mancanti")
	private List<Integer> authorsId;
	
}
