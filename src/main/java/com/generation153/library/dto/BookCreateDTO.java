package com.generation153.library.dto;

import java.util.List;

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

	private String isbn;
	private String title;
	private String language;
	private String imageUri;
	private Integer edition;
	private Boolean lendable;
	
	private Integer publisherId;
	private Integer categoryId;
	private List<Integer> authorsId;
}
