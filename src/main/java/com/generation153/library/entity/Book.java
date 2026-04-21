package com.generation153.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, length = 20, unique = true)
	private String isbn;
	@Column(nullable = false, length = 256)
	private String title;
	@Column(nullable = false, length = 20)
	private String language;
	@Column(name = "image_uri", nullable = false, length = 256)
	private String imageUri;
	@Column(nullable = false, length = 4)
	private int edition;
	@Column(nullable = false)
	private boolean lendable;

}
